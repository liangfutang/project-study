package com.zjut.study.nio.components;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.nio.utils.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ByteBuffer 组件的相关测试
 * @author jack
 */
@Slf4j
public class ByteBufferClient extends CommonJunitFilter {

    /**
     * 用作 ByteBuffer 读取相关操作
     */
    private ByteBuffer readBuffer;
    @Before
    public void before() {
        // 初始化读取操作的相关数据
        readBuffer = ByteBuffer.allocate(10);
        readBuffer.put(new byte[]{'a', 'b', 'c', 'd'});
        readBuffer.flip();
    }

    /**
     * 把文件中存储的14个字节的字符串读取出来，用小于14字节的缓冲区多次接受
     */
    @Test
    public void readFile() {
        try(
                FileInputStream in = new FileInputStream("src/main/resources/iofiles/data.txt");
                FileChannel channel = in.getChannel();
        ) {
            // 当前文件中存储有 14 个字节的字符串，现分配十个字节的缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
                int len = channel.read(buffer);
                log.info("本次读取到的字节数:{}", len);
                if (-1 == len) {
                    break;
                }
                // 将本次读取到的内容输出
                buffer.flip(); // 切换到读模式
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    log.info("实际字节:{}", (char)b);
                }
                // 清空缓存
                buffer.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ByteBuffer申请不同位置的空间
     *  class java.nio.HeapByteBuffer    - java 堆内存，读写效率较低，受到 GC 的影响
     *  class java.nio.DirectByteBuffer  - 直接内存，读写效率高（少一次拷贝），不会受 GC 影响，分配的效率低 (需要调用操作系统的函数)
     */
    @Test
    public void allocateLocation() {
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());
    }

    /**
     * 网络数据传输，数据之间使用 \n 进行分隔，在接收时，被进行了重新组合，导致粘包半包，例如原始数据有3条为
     *     Hello,world\n
     *     I'm zhangsan\n
     *     How are you?\n
     * 变成了下面的两个 byteBuffer (黏包，半包)
     *     Hello,world\nI'm zhangsan\nHo
     *     w are you?\n
     * 现将错乱的数据恢复成原始的按 \n 分隔的数据
     */
    @Test
    public void compact() {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        this.spilt(buffer);
        buffer.put("w are you?\n".getBytes());
        this.spilt(buffer);
    }


    /**
     * rewind 从头开始读
     */
    @Test
    public void readRewind() {
        ByteBufferUtil.debugAll(readBuffer);
        // 读完后position的位置到读取的最后位置了
        // get(byte[] dst)会改变position的位置，但是get(int index)不会改变position的位置
        readBuffer.get(new byte[4]);
        // 会将position重新置到第一位
        readBuffer.rewind();
        ByteBufferUtil.debugAll(readBuffer);
        System.out.println((char)readBuffer.get());
    }

    /**
     * mark & reset
     * mark 做一个标记，记录 position 位置， reset 是将 position 重置到 mark 的位置
     */
    @Test
    public void readMark() {
        System.out.println((char) readBuffer.get());
        System.out.println((char) readBuffer.get());
        System.out.println("==============标记============");
        readBuffer.mark();
        System.out.println((char) readBuffer.get());
        System.out.println((char) readBuffer.get());
        System.out.println("==============重置===========");
        // 重置后 position 会移动到标记的位置
        readBuffer.reset();
        System.out.println((char) readBuffer.get());
        System.out.println((char) readBuffer.get());
    }



//==========================================内部使用的方法====================================================

    private void spilt(ByteBuffer source) {
        source.flip();
        for (int i=0; i<source.limit(); i++) {
            // 找到一条完整的消息， get(i) 方法不会改变position位置
            if ('\n' == source.get(i)) {
                int len = i + 1 - source.position();
                // 将该条完整的消息单独存放
                ByteBuffer target = ByteBuffer.allocate(len);
                for (int j=0; j<len; j++) {
                    target.put(source.get());
                }
                ByteBufferUtil.debugAll(target);
            }
        }
        source.compact();
    }
}
