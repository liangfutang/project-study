package com.zjut.study.netty.components.bytebuf;

import com.zjut.study.common.junit.CommonJunitFilter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
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
}
