package com.zjut.study.netty.advance.c1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 使用EmbeddedChannel测试netty的解包  LengthFieldBasedFrameDecoder
 */
@Slf4j
public class TestLengthFieldDecoder {

    @Test
    public void test01() {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 1,4),
                new LoggingHandler()
        );

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        send(buffer, "hello world!");
        send(buffer, "hi!");

        channel.writeInbound(buffer);
    }

    private static void send(ByteBuf buffer, String content) {
        byte[] bytes = content.getBytes(); // 实际内容
        int length = bytes.length; // 实际内容长度
        buffer.writeInt(length);  // 写缓存的长度
        buffer.writeByte(1);  // 添加字段
        buffer.writeBytes(bytes);  // 写缓存的内容
    }
}
