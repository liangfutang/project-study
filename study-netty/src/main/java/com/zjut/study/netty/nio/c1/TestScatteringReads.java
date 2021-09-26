package com.zjut.study.netty.nio.c1;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static com.zjut.study.netty.ByteBufferUtil.debugAll;

/**
 * 分开接收
 */
public class TestScatteringReads {

    /**
     * 将文件中内容读取出来，分开接收
     */
    @Test
    public void test01() {
        ByteBuffer b1_ = ByteBuffer.allocate(3);
        ByteBuffer b2_ = ByteBuffer.allocate(3);
        ByteBuffer b3_ = ByteBuffer.allocate(5);

        try (FileChannel channel = new RandomAccessFile("src/main/java/com/zjut/study/netty/data/words.txt", "r").getChannel()) {
            channel.read(new ByteBuffer[]{b1_, b2_, b3_});
            b1_.flip();
            b2_.flip();
            b3_.flip();

            debugAll(b1_);
            debugAll(b2_);
            debugAll(b3_);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
