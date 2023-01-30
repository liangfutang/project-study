package com.zjut.study.nio.components;

import com.zjut.study.common.junit.CommonJunitFilter;
import lombok.extern.slf4j.Slf4j;
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
}
