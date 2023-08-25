package com.zjut.study.netty.advance.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Random;

/**
 * 使用固定长度解决黏包半包问题客户端
 */
@Slf4j
public class Client2 {

    public static void main(String[] args) {
        send();
        System.out.println("finish");
    }

    private static void send() {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ChannelFuture channelFuture = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nsc) throws Exception {
                            nsc.pipeline().addLast(new LoggingHandler());
                            nsc.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                // 会在连接 channel 建立成功后，会触发 active 事件
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ByteBuf buffer = ctx.alloc().buffer();
                                    char x = '0';
                                    Random random = new Random();
                                    for (int i=0; i<10; i++) {
                                        byte[] bytes = fill10Bytes(x, random.nextInt(10) + 1);
                                        x++;
                                        buffer.writeBytes(bytes);
                                    }
                                    ctx.writeAndFlush(buffer);  // 向服务端发送 10*10个byte
                                }
                            });
                        }
                    }).connect(new InetSocketAddress("localhost", 8080));
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    /**
     * 对不足位的补齐操作
     * @param c
     * @param len
     * @return
     */
    public static byte[] fill10Bytes(char c, int len) {
        byte[] bytes = new byte[10];
        Arrays.fill(bytes, (byte) '_');
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) c;
        }
        System.out.println(new String(bytes));
        return bytes;
    }
}
