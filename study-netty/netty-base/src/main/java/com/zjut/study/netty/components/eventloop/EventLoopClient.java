package com.zjut.study.netty.components.eventloop;

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

/**
 * EventLoop 客户端
 */
@Slf4j
public class EventLoopClient {

    /**
     * 使用 sync 方式阻塞 给服务端发送消息
     */
    @Test
    public void client01() throws InterruptedException, IOException {
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
                .connect(new InetSocketAddress(8080))
                // 使用 sync 方法同步处理结果
                // 阻塞住当前线程，直到nio线程连接建立完毕
                .sync()
                .channel();

        channel.writeAndFlush("hello world !");
        Thread.sleep(3000);
        channel.writeAndFlush("hi !");

        System.in.read();
    }
}
