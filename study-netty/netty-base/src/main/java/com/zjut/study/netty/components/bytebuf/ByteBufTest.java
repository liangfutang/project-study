package com.zjut.study.netty.components.bytebuf;

import com.zjut.study.common.junit.CommonJunitFilter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static com.zjut.study.netty.utils.ByteBufferUtil.logByteBuf;

@Slf4j
public class ByteBufTest extends CommonJunitFilter {

    @Test
    public void test01() {
        // 默认池池化，通过启动jvm参数修改 -Dio.netty.allocator.type={unpooled|pooled}

        // 默认是256 大小,动态扩容
        // 池化直接内存 com.zjut.study.netty.components.bytebuf.ByteBufTest - buf类型:PooledUnsafeDirectByteBuf(ridx: 0, widx: 0, cap: 256)
//        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        ByteBuf buffer = ByteBufAllocator.DEFAULT.directBuffer();

        // 池化堆内存 com.zjut.study.netty.components.bytebuf.ByteBufTest - buf类型:PooledUnsafeHeapByteBuf(ridx: 0, widx: 0, cap: 256)
//        ByteBuf buffer = ByteBufAllocator.DEFAULT.heapBuffer();
        log.info("buf类型:" + buffer.toString());

        logByteBuf(buffer);

        StringBuilder builder = new StringBuilder();
        for (int i=0; i<300; i++) {
            builder.append(i);
        }
        buffer.writeBytes(builder.toString().getBytes());
        logByteBuf(buffer);
    }

    /**
     * 合并buf
     */
    @Test
    public void composite() {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{1,2,3,4,5});

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf2.writeBytes(new byte[]{6,7,8,9,10});

        // 底层会产生复制，不是使用相同内存
//        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
//        buffer.writeBytes(buf1).writeBytes(buf2);
//        logByteBuf(buffer);

        // 底层使用相同的内存
        CompositeByteBuf buffer = ByteBufAllocator.DEFAULT.compositeBuffer();
        buffer.addComponents(true, buf1, buf2);
        logByteBuf(buffer);
    }
}
