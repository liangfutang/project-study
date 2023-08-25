package com.zjut.study.netty.netty.c2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.locks.LockSupport;


@Slf4j
public class EventLoopClient {

    /**
     * 使用 sync 方式阻塞 给服务端发送消息
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    public void test01() throws InterruptedException, IOException {
        Channel channel = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nsc) throws Exception {   // 在连接建立后被调用
                        nsc.pipeline().addLast(new StringEncoder());
                    }
                })
                // 1. 连接到服务器
                // 异步非阻塞, main 发起了调用，真正执行 connect 是 nio 线程
                .connect(new InetSocketAddress("localhost", 8080))
                // 2.1 使用 sync 方法同步处理结果
                .sync()  // 阻塞住当前线程，直到nio线程连接建立完毕
                .channel();

        channel.writeAndFlush("hello world !");
        Thread.sleep(1000);
        channel.writeAndFlush("hi");

        System.in.read();
    }

    /**
     * 使用监听回调方式向服务端写数据
     */
    @Test
    public void test02() throws IOException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nsc) throws Exception {
                        nsc.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080));

        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel = channelFuture.channel();
                log.info("连接上channel:{}", channel);
                channel.writeAndFlush("hello world !");
            }
        });

        System.in.read();
    }

    /**
     * 控制台向服务端写入数据
     */
    @Test
    public void test03() throws IOException, InterruptedException {
        Thread currentThread = Thread.currentThread();

        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nsc) throws Exception {
                        nsc.pipeline().addLast(new StringEncoder());
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
                    log.info("结束连接");
                    channel.close(); // 异步操作，此处后面的代码不一定是在该行代码后面运行
                    scanner.close();
                    LockSupport.unpark(currentThread);
                    break;
                }

                channel.writeAndFlush(line); // 向服务端写入
            }

        }).start();

        LockSupport.park();
    }

    /**
     * 客户端和服务端断开连接后优雅的处理关闭资源等相关操作
     * @throws InterruptedException
     */
    @Test
    public void test04() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nsc) throws Exception {
                        nsc.pipeline().addLast(new LoggingHandler());
                        nsc.pipeline().addLast(new StringEncoder());
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
                    channel.close();  // 开始异步的断开操作
                    log.info("即将开始和服务端断开...");
                }
                channel.writeAndFlush(line);
            }
        }).start();

        // 获取 CloseFuture 对象， 1) 同步处理关闭， 2) 异步处理关闭
        ChannelFuture closeFuture = channel.closeFuture();

        // 方式一.
        closeFuture.sync(); // 处理关闭之后的操作
        log.info("和服务端断开连接");
        group.shutdownGracefully();  // 优雅的关闭group
        log.info("关闭完成");

        // 方式二. 此处需要想办法暂停主线程(main方法或使用Park等)
//        closeFuture.addListener((ChannelFutureListener) future -> {
//            log.info("开始断开操作");
//            group.shutdownGracefully();
//        });
    }

}
