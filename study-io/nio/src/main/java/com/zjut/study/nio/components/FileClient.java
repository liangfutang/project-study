package com.zjut.study.nio.components;

import com.zjut.study.common.junit.CommonJunitFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * File及Channel相关
 * @author tlf
 */
@Slf4j
public class FileClient extends CommonJunitFilter {

    @Test
    public void fileChannelTransferTo() {
        try (
                FileChannel from = new RandomAccessFile("C:\\Users\\tlf\\Desktop\\code\\words2.txt", "r").getChannel();
                FileChannel to = new RandomAccessFile("C:\\Users\\tlf\\Desktop\\code\\to.txt", "rw").getChannel();
        ) {
            // 效率高，底层会利用操作系统的零拷贝进行优化, 一次最多 2g 数据
            long size = from.size();
            // left 变量代表还剩余多少字节
            for (long left=size; left>0;) {
                log.info("position:{},left:{}", (size-left), left);
                left -= from.transferTo(size-left, left, to);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
