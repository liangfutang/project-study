package com.zjut.study.netty.netty.c2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
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
                    channel.close(); // 异步操作
                    scanner.close();
                    LockSupport.unpark(currentThread);
                    break;
                }

                channel.writeAndFlush(line); // 向服务端写入
            }

        }).start();

        LockSupport.park();
    }

}
