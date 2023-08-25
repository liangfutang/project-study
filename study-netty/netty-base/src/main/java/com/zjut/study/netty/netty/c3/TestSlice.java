package com.zjut.study.netty.netty.c3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static com.zjut.study.netty.utils.ByteBufferUtil.logByteBuf;

/**
 * 测试bytebuffer的切片
 */
@Slf4j
public class TestSlice {

    /**
     * 对初始bytebuffer切片
     */
    @Test
    public void test01() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});  // 添加原始数据
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
     * @throws InterruptedException
     */
    @Test
    public void test02() throws InterruptedException {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});  // 添加原始数据
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
    public void test03() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});  // 添加原始数据
        log.info("原始数据");
        logByteBuf(buffer);

        ByteBuf f1 = buffer.slice(0, 5);
        buffer.release();

        logByteBuf(f1); // 内存已经释放，读取不到了，会产生异常 IllegalReferenceCountException
    }

    /**
     * 对切片添加新内容
     */
    @Test
    public void test04() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});  // 添加原始数据
        log.info("原始数据");
        logByteBuf(buffer);

        ByteBuf f1 = buffer.slice(0, 5);
        f1.writeByte('x');

        logByteBuf(f1); // 切分后不能添加，会产生异常 IndexOutOfBoundsException
    }

    @Test
    public void test05() throws InterruptedException {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});  // 添加原始数据
        log.info("原始数据");
        logByteBuf(buffer);

        ByteBuf f1 = buffer.slice(0, 5);
        f1.retain();
        ByteBuf f2 = buffer.slice(5, 5);
        f2.retain();

        buffer.release();  // 释放内存

        Thread.sleep(1000);
        log.info("释放内存后");

//        f1.release();  // 加过的如果在不用后不减一，会导致内存不会释放，此处如果放开，则会成功释放内存，则后面会读取不到内容而异常
//        f2.release();

        logByteBuf(buffer);
        logByteBuf(f1);
        logByteBuf(f2);
    }
}
