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

    @Test
    public void slice01() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        log.info("原始数据");
        logByteBuf(buffer);

        // 在切片过程中，没有发生数据复制
        ByteBuf f1 = buffer.slice(0, 5);
        ByteBuf f2 = buffer.slice(5, 5);

        logByteBuf(f1);
        logByteBuf(f2);
    }

    /**
     * 对切片内容做替换，因为切片使用的和原始是相同内存，所以修改其中一个则都改了
     */
    @Test
    public void slice02() throws InterruptedException {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        log.info("原始数据");
        logByteBuf(buffer);

        // 在切片过程中，没有发生数据复制
        ByteBuf f1 = buffer.slice(0, 5);
        logByteBuf(f1);

        f1.setByte(0, 'x');
        Thread.sleep(1000);

        log.info("替换后");
        logByteBuf(buffer);
        logByteBuf(f1);
    }

    /**
     * 内容释放后再次读取
     */
    @Test
    public void slice03() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        log.info("原始数据");
        logByteBuf(buffer);

        ByteBuf f1 = buffer.slice(0, 5);
        buffer.release();

        // 内存已经释放，读取不到了，会产生异常 IllegalReferenceCountException
        logByteBuf(f1);
    }

    @Test
    public void slice04() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        log.info("原始数据");
        logByteBuf(buffer);

        ByteBuf f1 = buffer.slice(0, 5);
        f1.writeByte('x');

        // 切分后不能添加，会产生异常 IndexOutOfBoundsException
        logByteBuf(f1);
    }

    @Test
    public void slice05() throws InterruptedException {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        log.info("原始数据");
        logByteBuf(buffer);

        ByteBuf f1 = buffer.slice(0, 5);
        // 多加一层引用
        f1.retain();
        ByteBuf f2 = buffer.slice(5, 5);
        f2.retain();

        // 释放内存，引用数减一
        buffer.release();

        Thread.sleep(1000);
        log.info("释放内存后");

        // 加过的如果在不用后不减一，会导致内存不会释放，此处如果放开，则会成功释放内存，则后面会读取不到内容而异常
        // f1.release();
        // f2.release();

        logByteBuf(buffer);
        logByteBuf(f1);
        logByteBuf(f2);
    }
}
