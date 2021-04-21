package com.zjut.study.io.nio.first;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Scanner;

@Slf4j
public class NIOClient {
    public static void main(String[] args) throws IOException {
        // 创建一个发送通道
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 7777));
        channel.configureBlocking(false);
        // 创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Scanner sin = new Scanner(System.in);
        String userName = "我是你高不可攀的二大爷";
        while (sin.hasNext()) {
            String content = sin.nextLine();

            buffer.put(("["+ LocalDateTime.now() + "] " + userName + ": " + content).getBytes());
            buffer.flip();
            channel.write(buffer);
            buffer.clear();
        }
        // 关闭通道
        channel.close();
    }
}
