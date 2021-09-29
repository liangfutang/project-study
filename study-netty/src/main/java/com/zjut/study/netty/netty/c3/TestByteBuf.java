package com.zjut.study.netty.netty.c3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static com.zjut.study.netty.ByteBufferUtil.logByteBuf;

/**
 * ByteBuf 的相关使用
 */
@Slf4j
public class TestByteBuf {

    @Test
    public void test01() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();  // 默认是256 大小,动态扩容
        logByteBuf(buffer);
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<30; i++) {
            sb.append("a");
        }
        buffer.writeBytes(sb.toString().getBytes());
        logByteBuf(buffer);
    }


}
