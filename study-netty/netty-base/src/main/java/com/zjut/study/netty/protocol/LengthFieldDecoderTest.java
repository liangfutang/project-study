package com.zjut.study.netty.protocol;

import com.zjut.study.common.junit.CommonJunitFilter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.Test;

import java.nio.charset.Charset;

public class LengthFieldDecoderTest extends CommonJunitFilter {

    @Test
    public void test01() {
        EmbeddedChannel channel = new EmbeddedChannel(
                // 使用帧解码器解决粘包 半包问题
                new LengthFieldBasedFrameDecoder(1024,0,4,0,4),
                new LoggingHandler(LogLevel.DEBUG),
                new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ByteBuf buf = (ByteBuf) msg;
                        System.out.println(buf.toString(Charset.defaultCharset()));
                    }
                }
        );

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeInt(12);
        buf.writeBytes("hello, world".getBytes());
        buf.writeInt(6);
        buf.writeBytes("你好".getBytes());
        channel.writeInbound(buf);
    }

    @Test
    public void test02() {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 1,4),
                new LoggingHandler()
        );

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        send(buffer, "hello world!");
        send(buffer, "hi!");

        channel.writeInbound(buffer);
    }

    private void send(ByteBuf buffer, String content) {
        // 实际的消息内容
        byte[] bytes = content.getBytes();
        // 内容的长度
        int length = bytes.length;
        // 写缓存的长度
        buffer.writeInt(length);
        // 添加字段
        buffer.writeByte(1);
        // 写缓存的内容
        buffer.writeBytes(bytes);
    }
}
