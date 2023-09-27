package com.zjut.study.netty.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 使用redis请求协议向redis写入数据，并读取反馈数据
 */
@Slf4j
public class RedisProtocol {

    /*
     set name zhangsan
     *3   // 将上述指令看做成一个数组，此处值表示该数组的长度
     $3   // 发送的每个命令或键值的长度
     set
     $4
     name
     $8
     zhangsan
      */
    @Test
    public void test01() {
        EventLoopGroup worker = new NioEventLoopGroup();
        // 回车换行的字符值，多个部分之间使用回车换行分隔
        final byte[] LINE = {13, 10};
        try {
            ChannelFuture channelFuture = new Bootstrap()
                    .group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new LoggingHandler());
                            channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                // 会在连接 channel 建立成功后，会触发 active 事件
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ByteBuf redisRequest = ctx.alloc().buffer();
                                    redisRequest.writeBytes("*3".getBytes());
                                    redisRequest.writeBytes(LINE);
                                    redisRequest.writeBytes("$3".getBytes());
                                    redisRequest.writeBytes(LINE);
                                    redisRequest.writeBytes("set".getBytes());
                                    redisRequest.writeBytes(LINE);
                                    redisRequest.writeBytes("$4".getBytes());
                                    redisRequest.writeBytes(LINE);
                                    redisRequest.writeBytes("name".getBytes());
                                    redisRequest.writeBytes(LINE);
                                    redisRequest.writeBytes("$8".getBytes());
                                    redisRequest.writeBytes(LINE);
                                    redisRequest.writeBytes("zhangsan".getBytes());
                                    redisRequest.writeBytes(LINE);
                                    ctx.writeAndFlush(redisRequest);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    ByteBuf message = (ByteBuf) msg;
                                    log.info("接受到redis的返回消息:{}", message.toString(Charset.defaultCharset()));
                                }
                            });
                        }
                    })
                    .connect(new InetSocketAddress("localhost", 6379))
                    .sync();

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

    @Test
    public void test02() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        byte[] LINE = {13, 10};

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        // 会在连接 channel 建立成功后，会触发 active 事件
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 向redis写数据
                            set(ctx);
                            // 从redis读数据
                            get(ctx);
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ByteBuf buf = (ByteBuf) msg;
                            System.out.println(buf.toString(Charset.defaultCharset()));
                        }

                        /**
                         * 使用set指令往redis写数据
                         * @param ctx
                         */
                        private void set(ChannelHandlerContext ctx) {
                            ByteBuf buf = ctx.alloc().buffer();
                            buf.writeBytes("*3".getBytes());
                            buf.writeBytes(LINE);
                            buf.writeBytes("$3".getBytes());
                            buf.writeBytes(LINE);
                            buf.writeBytes("set".getBytes());
                            buf.writeBytes(LINE);
                            buf.writeBytes("$3".getBytes());
                            buf.writeBytes(LINE);
                            buf.writeBytes("aaa".getBytes());
                            buf.writeBytes(LINE);
                            buf.writeBytes("$3".getBytes());
                            buf.writeBytes(LINE);
                            buf.writeBytes("bbb".getBytes());
                            buf.writeBytes(LINE);
                            ctx.writeAndFlush(buf);
                        }

                        /**
                         * 使用get指令从redis读数据
                         * @param ctx
                         */
                        private void get(ChannelHandlerContext ctx) {
                            ByteBuf buf = ctx.alloc().buffer();
                            buf.writeBytes("*2".getBytes());
                            buf.writeBytes(LINE);
                            buf.writeBytes("$3".getBytes());
                            buf.writeBytes(LINE);
                            buf.writeBytes("get".getBytes());
                            buf.writeBytes(LINE);
                            buf.writeBytes("$3".getBytes());
                            buf.writeBytes(LINE);
                            buf.writeBytes("aaa".getBytes());
                            buf.writeBytes(LINE);
                            ctx.writeAndFlush(buf);
                        }
                    });
                }
            });

            ChannelFuture channelFuture = bootstrap.connect("localhost", 6379).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }
}
