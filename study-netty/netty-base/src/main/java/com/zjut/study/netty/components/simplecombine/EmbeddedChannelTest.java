package com.zjut.study.netty.components.simplecombine;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

/**
 * netty提供的对 pipeline 的测试类
 */
@Slf4j
public class EmbeddedChannelTest {

    @Test
    public void test01() {
        // 入站
        ChannelInboundHandlerAdapter h1 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.info("1");
                super.channelRead(ctx, msg);
            }
        };
        ChannelInboundHandlerAdapter h2 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.info("2");
                super.channelRead(ctx, msg);
            }
        };

        // 出战
        ChannelOutboundHandlerAdapter h3 = new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                log.info("3");
                super.write(ctx, msg, promise);
            }
        };
        ChannelOutboundHandlerAdapter h4 = new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                log.info("4");
                super.write(ctx, msg, promise);
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(h1, h2, h3, h4);
        // 模拟入栈,不能模拟出栈
        log.info("开始模拟入栈操作");
        channel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("hello".getBytes()));
        // 模拟出栈操作
        log.info("开始模拟出栈操作");
        channel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("world".getBytes()));
    }

    @Test
    public void test02() throws IOException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        EmbeddedChannel c1 = new EmbeddedChannel(false,false,
                new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.debug("{}",msg);
                        super.channelRead(ctx, msg);
                    }
                }
        );
        EmbeddedChannel c2 = new EmbeddedChannel(
                new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.debug("{}",msg);
                        super.channelRead(ctx, msg);
                    }
                }
        );
        EmbeddedChannel c3 = new EmbeddedChannel(
                new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.debug("{}",msg);
                        super.channelRead(ctx, msg);
                    }
                }
        );

        group.next().register(c1);
        group.next().register(c2);
        group.next().register(c3);

        c1.writeInbound("1");
        c2.writeInbound("2");
        c3.writeInbound("3");
        c1.writeInbound("1");
        c2.writeInbound("2");
        c3.writeInbound("3");

        System.in.read();
    }
}
