package com.zjut.study.netty.netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 服务的
 */
@Slf4j
public class HelloServer {

    /**
     * 此处只能用main执行，用@Test注解会导致直接关闭
     * @param args
     */
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
                        nc.pipeline().addLast(new LoggingHandler());
                        nc.pipeline().addLast(new StringDecoder());  // 将ByteBuf转为字符串
                        nc.pipeline().addLast(new ChannelInboundHandlerAdapter() {  // 自定义handler
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
