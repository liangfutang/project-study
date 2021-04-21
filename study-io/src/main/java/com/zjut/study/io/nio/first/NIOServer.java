package com.zjut.study.io.nio.first;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

@Slf4j
public class NIOServer {
    public static void main(String[] args) throws IOException {

        // 建服务端通道
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(7777));
        // 注册多路选择器
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        // 轮询
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                
                // 事件处理
                if (next.isAcceptable()) {
                    // 接收到一个连接
                    SocketChannel socketChannel = ssc.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    log.info("有一个小家伙上线啦");
                } else if (next.isReadable()) {
                    new Thread(new NIOServerRead(next)).start();
                }
            }
            iterator.remove();
        }

        ssc.close();
        selector.close();
    }
}
