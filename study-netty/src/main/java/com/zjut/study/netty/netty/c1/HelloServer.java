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
        // 1. 启动器，负责组装 netty 组件，启动服务器
        new ServerBootstrap()
                // 2. (selector,thread), group 组
                .group(new NioEventLoopGroup())
                // 3. 选择 服务器的 ServerSocketChannel 实现
                .channel(NioServerSocketChannel.class)  // OIO BIO
                // 4. boss 负责处理连接， worker(child) 负责处理读写，决定了 worker(child) 能执行哪些操作（handler）
                // 5. channel 代表和客户端进行数据读写的通道   Initializer 初始化，负责添加别的 handler
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
                        // 6. 添加具体 handler
                        nc.pipeline().addLast(new LoggingHandler());
                        nc.pipeline().addLast(new StringDecoder());  // 将ByteBuf转为字符串
                        nc.pipeline().addLast(new ChannelInboundHandlerAdapter() {  // 自定义handler
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {   // 读事件
                                System.out.println(msg);
                            }
                        });
                    }
                })
                // 7. 绑定监听端口
                .bind(8080);
    }
}
