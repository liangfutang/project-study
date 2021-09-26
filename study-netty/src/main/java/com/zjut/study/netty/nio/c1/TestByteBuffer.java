package com.zjut.study.netty.nio.c1;

import com.zjut.study.common.junit.CommonJunitFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 简单的对ByteBuffer使用
 */
@Slf4j
public class TestByteBuffer extends CommonJunitFilter {

    /**
     * 功能：将文件中的内容读取到控制面板
     * 内部结构：使用一个ByteBuffer中间缓存多次接收
     */
    @Test
    public void test01() {
        try (FileChannel channel = new FileInputStream("src/main/java/com/zjut/study/netty/data/test1.txt").getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(16); // 中间缓存
            while (true) {
                int read = channel.read(buffer);
                if (read == -1) break;  //防止死循环

                buffer.flip();  // 切换到写模式
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    log.info("实际的字节:{}", (char) b);
                }
                buffer.clear(); // 清空缓存，切换为写模式
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
