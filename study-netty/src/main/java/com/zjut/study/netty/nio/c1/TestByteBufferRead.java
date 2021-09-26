package com.zjut.study.netty.nio.c1;

import org.junit.Test;

import java.nio.ByteBuffer;

import static com.zjut.study.netty.ByteBufferUtil.debugAll;

/**
 * 读取的一些api方法测试
 */
public class TestByteBufferRead {

    /**
     * 在缓存中使用带索引的获取不会改变position的位置
     */
    @Test
    public void test01() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        buffer.flip();
        debugAll(buffer);
        System.out.println("获取到对应位置的字符:" + (char) buffer.get(3));
        debugAll(buffer); // 和上面的debugAll结果一样，没受到上一个get的影响
    }

    /**
     * rewind（） 对position位置的重置，是得读取过的可以重复读取
     */
    @Test
    public void test02() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        buffer.flip();
        debugAll(buffer);  // position: [0], limit: [4]

        System.out.println("读取两个字符:" + (char) buffer.get() + " " + (char) buffer.get());

        debugAll(buffer);// position: [2], limit: [4]

        System.out.println("重置position......");

        buffer.rewind();  // 重置position位置
        debugAll(buffer);  // position: [0], limit: [4]
        System.out.println("读取重置后的两个字符:" + (char) buffer.get() + " " + (char) buffer.get());
    }

    /**
     * mark & reset
     * mark 做一个标记，记录 position 位置， reset 是将 position 重置到 mark 的位置
     */
    @Test
    public void test03() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd', 'e', 'f'});
        buffer.flip();
        debugAll(buffer); // [0], limit: [6]

        System.out.println("先消费两个:" + (char) buffer.get() + " " + (char) buffer.get());  // 先消费两个:a b
        debugAll(buffer); // position: [2], limit: [6]
        buffer.mark();  // 添加对position的位置标记

        System.out.println("再消费两个:" + (char) buffer.get() + " " + (char) buffer.get());  // 再消费两个:c d
        buffer.reset(); // 复位到标记位
        debugAll(buffer);  // position: [2], limit: [6]

        System.out.println("消费复位的两个:" + (char) buffer.get() + " " + (char) buffer.get());  // 消费复位的两个:c d
        debugAll(buffer);  // position: [4], limit: [6]
    }
}
