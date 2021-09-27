package com.zjut.study.netty.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * 客户端
 */
@Slf4j
public class HelloClient {

    @Test
    public void test01() throws InterruptedException, IOException {
        new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
                        nc.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080))
                .sync()
                .channel()
                .writeAndFlush("hello world!");

        System.in.read();  // 卡一下，防止主线程先结束导致连接中断
    }
}
