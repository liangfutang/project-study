package com.zjut.study.netty.components.simplecombine;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * 简单的串联各个主要组件做成服务器
 */
public class Server {

    /**
     * 不能使用@Test，会导致服务器直接关闭
     */
    public static void main(String[] args) {
        // 服务端启动器，负责组装netty组件，启动服务器
        new ServerBootstrap()
                // (selector,thread)group组
                .group(new NioEventLoopGroup())
                // 选择服务器的channel实现
                .channel(NioServerSocketChannel.class)
                // boss 负责处理连接， worker(child) 负责处理读写，决定了 worker(child) 能执行哪些操作（handler）
                // channel 代表和客户端进行数据读写的通道   Initializer 初始化，负责添加别的 handler
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new LoggingHandler());
                        // 将ByteBuf转为字符串
                        channel.pipeline().addLast(new StringDecoder());
                        channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {  // 自定义handler
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                // 监听端口
                .bind(8080);
    }



}
