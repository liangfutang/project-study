package com.zjut.study.netty.netty.c3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static com.zjut.study.netty.utils.ByteBufferUtil.logByteBuf;

/**
 * 对compositeBuffer的测试
 * 和Slice相反，将多个合并
 */
@Slf4j
public class TestCompositeByteBuf {

    @Test
    public void test01() {
        // 生成多个测试数据
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{1, 2, 3, 4, 5});

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf2.writeBytes(new byte[]{6, 7, 8, 9, 10});

        // 合并   底层会产生复制，不是使用相同内存
//        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
//        buffer.writeBytes(buf1).writeBytes(buf2);
//        logByteBuf(buffer);

        // 合并  底层使用相同的内存
        CompositeByteBuf buffer = ByteBufAllocator.DEFAULT.compositeBuffer();
        buffer.addComponents(true, buf1, buf2);  // 如果不添加true参数，会导致合并不成功
        logByteBuf(buffer);
    }

}
