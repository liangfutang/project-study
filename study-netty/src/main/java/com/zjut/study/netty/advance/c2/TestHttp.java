package com.zjut.study.netty.advance.c2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 简单http协议服务器
 */
@Slf4j
public class TestHttp {

    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ChannelFuture channelFuture = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nsc) throws Exception {
                            nsc.pipeline().addLast(new LoggingHandler());
                            nsc.pipeline().addLast(new HttpServerCodec());
                            // 方式一. 使用netty提供的handler
                            nsc.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>(){
                                @Override
                                protected void channelRead0(ChannelHandlerContext chc, HttpRequest httpRequest) throws Exception {
                                    log.info("接收到请求的url:{}, method:{}", httpRequest.uri(), httpRequest.method());
                                    byte[] content = "<h1>hello world !</h1>".getBytes();
                                    DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.OK);
                                    response.headers().addInt(HttpHeaderNames.CONTENT_LENGTH, content.length);  // 告知浏览器本次将返回多少内容，否则浏览器将会一直等待中
                                    response.content().writeBytes(content);

                                    // 向请求源发送回数据
                                    chc.writeAndFlush(response);
                                }
                            });

                            // 方式二. 使用原始的方式，接收进来自己判断   直接接收自己判断并回写
//                            nsc.pipeline().addLast(new ChannelInboundHandlerAdapter(){
//                                @Override
//                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                    log.info("浏览器请求的数据:{}", msg.getClass());  // DefaultHttpRequest LastHttpContent$1
//                                    if (msg instanceof HttpRequest) {
//                                        byte[] content = "<h1>hello world !</h1>".getBytes();
//                                        msg = (HttpRequest) msg;
//                                        DefaultFullHttpResponse response = new DefaultFullHttpResponse(((HttpRequest) msg).protocolVersion(), HttpResponseStatus.OK);
//                                        response.headers().addInt(HttpHeaderNames.CONTENT_LENGTH, content.length);
//                                        response.content().writeBytes(content);
//
//                                        // 向浏览器写回数据
//                                        ctx.writeAndFlush(response);
//                                    } else if (msg instanceof HttpContent) {
//
//                                    }
//
//                                }
//                            });
                        }
                    })
                    .bind(8080).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
