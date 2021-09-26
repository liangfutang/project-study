package com.zjut.study.netty.nio.c3;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {

    @Test
    public void test01() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("localhost", 8080));

        System.out.println("waiting...");
//        channel.close();  //正常关闭连接
    }
}
