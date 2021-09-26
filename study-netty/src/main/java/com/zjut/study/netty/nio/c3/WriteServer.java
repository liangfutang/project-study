package com.zjut.study.netty.nio.c3;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * 写 服务器
 */
public class WriteServer {

    @Test
    public void test01() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 拿到后删除已经无用的key,否则最终会导致oom
                iterator.remove();
                // 判断是什么性质的事件
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, SelectionKey.OP_READ);

                    // 准备将要发送给客户端的数据
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 5000000; i++) {
                        sb.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());

                    // 向客户端写入数据
                    int write = sc.write(buffer);  // 返回实际已经写入了的字节数
                    System.out.println(write);

                    // 如果当前一次没写完
                    if (buffer.hasRemaining()) {
                        // 添加对可写事件的关注
                        scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                        // 将未写完的数据挂载到scKey上
                        scKey.attach(buffer);
                    }
                } else if (key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel channel = (SocketChannel) key.channel();

                    int write = channel.write(buffer);
                    System.out.println(write);

                    // 写完后清理操作
                    if (!buffer.hasRemaining()) {
                        key.attach(null); // 清理关联上的buffer
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE); // 取消关注的写事件
                    }

                }

            }
        }

    }
}
