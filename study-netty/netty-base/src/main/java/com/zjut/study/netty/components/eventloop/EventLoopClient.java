package com.zjut.study.netty.components.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.locks.LockSupport;

/**
 * EventLoop 客户端
 */
@Slf4j
public class EventLoopClient {

    /**
     * 使用 sync 方式阻塞 给服务端发送消息
     */
    @Test
    public void write01() throws InterruptedException, IOException {
        Channel channel = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    // 在连接建立后被调用
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new StringEncoder());
                    }
                })
                // 连接到服务器
                // 异步非阻塞, main 发起了调用，真正执行 connect 是 nio 线程
                .connect(new InetSocketAddress("localhost", 8080))
                // 使用 sync 方法同步处理结果
                // 阻塞住当前线程，直到nio线程连接建立完毕
                .sync()
                .channel();

        channel.writeAndFlush("hello world !");
        Thread.sleep(3000);
        channel.writeAndFlush("hi !");

        System.in.read();
    }

    /**
     * 使用监听回调方式向服务端写数据
     */
    @Test
    public void write02() throws IOException {
        ChannelFuture future = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080));

        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel = channelFuture.channel();
                log.info("连接上服务器:{}", channel);
                channel.writeAndFlush("hello world!");
            }
        });

        System.in.read();
    }

    @Test
    public void write03() throws InterruptedException {
        Thread currentThread = Thread.currentThread();

        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080));

        Channel channel = channelFuture.sync().channel();
        log.info("当前连接上的channel:{}", channel);

        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("Q".equals(line)) {
                    log.info("断开连接");
                    // 异步操作，此处后面的代码不一定是在该行代码后面运行
                    channel.close();
                    scanner.close();
                    LockSupport.unpark(currentThread);
                    break;
                }

                // 向服务端写入数据
                channel.writeAndFlush(line);
            }

        }).start();

        LockSupport.park();
    }

    @Test
    public void write04() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080));

        Channel channel = channelFuture.sync().channel();
        log.info("当前连接上channel:{}", channel);

        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("Q".equals(line)) {
                    // 开始异步断开操作
                    channel.close();
                    log.info("即将开始和服务端断开...");
                }
                channel.writeAndFlush(line);
            }
        }).start();

        // 获取 CloseFuture 对象， 1) 同步处理关闭， 2) 异步处理关闭
        ChannelFuture closeFuture = channel.closeFuture();

        // 方式一.
        // 处理关闭之后的操作，同步等待channel关闭
        closeFuture.sync();
        log.info("和服务器断开连接");
        // 优雅的关闭group
        group.shutdownGracefully();
        log.info("关闭完成");

        // 方式二. 此处需要想办法暂停主线程(main方法或使用Park等)，异步等待channel关闭
//        closeFuture.addListener((ChannelFutureListener) future -> {
//            log.info("开始断开操作");
//            group.shutdownGracefully();
//        });
    }
}
