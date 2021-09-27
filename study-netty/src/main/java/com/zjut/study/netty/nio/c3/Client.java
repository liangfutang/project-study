package com.zjut.study.netty.nio.c3;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {

    @Test
    public void test01() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("localhost", 8081));

        int hi = channel.write(StandardCharsets.UTF_8.encode("hi"));
        System.out.println("写出结果:"+ hi);

        System.out.println("waiting...");
//        channel.close();  //正常关闭连接
    }
}
