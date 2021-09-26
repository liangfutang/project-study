package com.zjut.study.netty.nio.c3;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class WriteClient {

    /**
     * 接收服务端发送过来的数据
     * @throws IOException
     */
    @Test
    public void test01() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("localhost", 8080));

        int count = 0;
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);

            int read = channel.read(buffer);

            count += read;
            System.out.println(count);
            buffer.clear();
        }
    }
}
