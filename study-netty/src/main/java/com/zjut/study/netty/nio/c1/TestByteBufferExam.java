package com.zjut.study.netty.nio.c1;

import org.junit.Test;

import java.nio.ByteBuffer;

import static com.zjut.study.netty.ByteBufferUtil.debugAll;

/**
 * 简单的对换行的处理
 */
public class TestByteBufferExam {

    /**
     *   网络上有多条数据发送给服务端，数据之间使用 \n 进行分隔
     *   但由于某种原因这些数据在接收时，被进行了重新组合，例如原始数据有3条为
     *       Hello,world\n
     *       I'm zhangsan\n
     *       How are you?\n
     *   变成了下面的两个 byteBuffer (黏包，半包)
     *       Hello,world\nI'm zhangsan\nHo
     *       w are you?\n
     *   现在要求你编写程序，将错乱的数据恢复成原始的按 \n 分隔的数据
     */
    @Test
    public void test01() {
        // 模拟一个收到的简单数据源
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }


    private void split(ByteBuffer source) {
        source.flip();// 切换到读模式
        for (int i=0; i<source.limit(); i++) {
            // 遍历出一个正常的一段
            if (source.get(i) == '\n') {  // 带索引的get方法获取不会修改ByteBuffer的position
                int length = i + 1 - source.position();
                // 临时存储其中的一段字符串
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j=0; j<length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        source.compact(); // 切换到拼接的写模式
    }



}
