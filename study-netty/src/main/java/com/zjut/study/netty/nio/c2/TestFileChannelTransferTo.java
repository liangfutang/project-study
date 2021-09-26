package com.zjut.study.netty.nio.c2;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 文件内容复制
 * 效率高，底层会利用操作系统的零拷贝进行优化, 2g 数据
 *
 */
public class TestFileChannelTransferTo {

    @Test
    public void test01() {
        try (
            FileChannel from = new FileInputStream("src/main/java/com/zjut/study/netty/data/test1.txt").getChannel();
            FileChannel to = new FileOutputStream("src/main/java/com/zjut/study/netty/data/to.txt").getChannel();
        ) {
            long size = from.size(); // from文件中大小
            for (long left=size; left>0;) {
                System.out.println("position:" + (size-left) + ",left:" + left);
                left -= from.transferTo((size-left), left, to); // 先迁移部分内容
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
