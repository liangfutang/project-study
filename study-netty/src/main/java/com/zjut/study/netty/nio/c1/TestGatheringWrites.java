package com.zjut.study.netty.nio.c1;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 合并写入
 */
public class TestGatheringWrites {

    /**
     * 将缓冲数组合并并写入
     */
    @Test
    public void test01() {
        ByteBuffer b1_ = StandardCharsets.UTF_8.encode("hello ");
        ByteBuffer b2_ = StandardCharsets.UTF_8.encode("world!");
        ByteBuffer b3_ = StandardCharsets.UTF_8.encode("你好呀");
        ByteBuffer b4_ = StandardCharsets.UTF_8.encode("你好");

        try (FileChannel channel = new RandomAccessFile("src/main/java/com/zjut/study/netty/data/test1.txt", "rw").getChannel()) {
            channel.write(new ByteBuffer[]{b1_, b2_, b3_, b4_});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
