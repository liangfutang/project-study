package com.zjut.study.netty.nio.c1;

import org.junit.Test;

import java.nio.ByteBuffer;

import static com.zjut.study.netty.ByteBufferUtil.debugAll;


public class TestByteBufferReadWrite {

    @Test
    public void test01() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61); // 'a'
        debugAll(buffer);

        buffer.put(new byte[]{0x62, 0x63, 0x64}); // b  c  d
        debugAll(buffer);
//        System.out.println(buffer.get());

        buffer.flip();
        System.out.println(buffer.get());
        debugAll(buffer);
        buffer.compact();

        debugAll(buffer);
        buffer.put(new byte[]{0x65, 0x6f});
        debugAll(buffer);
    }
}
