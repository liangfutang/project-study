package com.zjut.study.netty;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class ClientTest {

    /**
     * 测试读取文件中内容，并打印出来
     */
    @Test
    public void test01() throws FileNotFoundException {
        try (FileChannel channel = new FileInputStream(new File("src/main/java/com/zjut/study/netty/data/test1.txt")).getChannel();) {
            ByteBuffer buffer = ByteBuffer.allocate(10);
            int len;
            while ((len = channel.read(buffer)) != -1) {
                buffer.flip(); // 切换为只读模式
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    System.out.println((char) b);
                }
                buffer.clear();
            }
        } catch (IOException e) {
            log.error("读取文件异常:", e);
        }
    }
}
