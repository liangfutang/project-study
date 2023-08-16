package com.zjut.study.nio.server.selector.read;

import com.zjut.study.nio.utils.ByteBufferUtil;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 接受到客户端的请求后接受读事件
 * @author tlf
 */
public class ReadServer {
    public static void main(String[] args) {
        try (ServerSocketChannel ssc = ServerSocketChannel.open();) {
            Selector selector = Selector.open();
            ssc.bind(new InetSocketAddress(8080));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                int select = selector.select();
                System.out.println("select count: " + select);

                Iterator<SelectionKey> sk = selector.selectedKeys().iterator();
                while (sk.hasNext()) {
                    SelectionKey key = sk.next();
                    sk.remove();

                    if (key.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel accept = channel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);
                        System.out.println("已经建立连接:" + accept);
                    } else if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(10);
                        int read = sc.read(buffer);
                        if (read == -1) {
                            key.cancel();
                            sc.close();
                        } else {
                            buffer.flip();
                            ByteBufferUtil.debugAll(buffer);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
