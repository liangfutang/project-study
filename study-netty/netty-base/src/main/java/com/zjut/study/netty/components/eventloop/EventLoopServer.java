package com.zjut.study.netty.components.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * EventLoopServer 服务端
 */
@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {
        // 创建一个独立的 EventLoopGroup，处理handler环节中耗时比较长的任务，防止因为处理业务导致连接阻塞
        EventLoopGroup group = new DefaultEventLoopGroup();
        new ServerBootstrap()
                // boss可以不用设置线程数为1，因为只有一个ServerSocket，所以只会到boss中找一个selector
                // boss 只负责 ServerSocketChannel 上 accept 事件     worker 只负责 socketChannel 上的读写
                // 同一个client会有同一个nioEventLoopGroup接收处理
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nsc) throws Exception {
                        nsc.pipeline().addLast("handler1", new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.info("handler1收到缓存:{}", buf.toString(Charset.defaultCharset()));
                                // 传递到下一个handler，如果不调，否则后面的handler不会被调用
                                ctx.fireChannelRead(msg);
                            }
                            // 单独线程处理handler2 业务
                        }).addLast(group, "handler2", new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.info("handler2收到缓存:{}", buf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
