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
                    // 因为 select 在事件发生后，就会将相关的 key 放入 selectedKeys 集合，但不会在处理完后从 selectedKeys 集合中移除，需要我们自己编码删除。例如
                    // 第一次触发了 ssckey 上的 accept 事件，没有移除 ssckey
                    // 第二次触发了 sckey 上的 read 事件，但这时 selectedKeys 中还有上次的 ssckey ，在处理时因为没有真正的 serverSocket 连上了，就会导致空指针异常
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
                            // cancel 会取消注册在 selector 上的 channel，并从 keys 集合中删除 key 后续不会再监听事件
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
