package com.zjut.study.netty.components.simplecombine;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) throws Exception {
        // 客户端启动类
        new Bootstrap()
                // 添加工作线程池
                .group(new NioEventLoopGroup())
                // 选择客户端channel实现
                .channel(NioSocketChannel.class)
                // 初始化处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception { // 在连接建立后被调用
                        channel.pipeline().addLast(new StringEncoder());
                    }
                })
                // 连接到服务器
                .connect(new InetSocketAddress("localhost", 8080))
                // 阻塞，直到连接建立
                .sync()
                // 向服务器发送数据
                .channel().writeAndFlush("hello world");

        // 卡住，防止因为主线程先结束导致连接中断
        System.in.read();
    }
}
