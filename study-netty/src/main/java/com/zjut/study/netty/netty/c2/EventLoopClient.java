package com.zjut.study.netty.netty.c2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;


@Slf4j
public class EventLoopClient {

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
                // 阻塞住当前线程，直到nio线程连接建立完毕
                .sync()
                .channel();

        channel.writeAndFlush("hello world !");
        Thread.sleep(1000);
        channel.writeAndFlush("hi");

        System.in.read();
    }

}
