package com.zjut.study.netty.nio.c3;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * 读 服务端
 */
@Slf4j
public class Server {

    @Test
    public void test01() throws IOException {
        // 1. 创建 selector, 管理多个 channel
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8080));

        // 2. 建立 selector 和 channel 的联系（注册）
        // SelectionKey 就是将来事件发生后，通过它可以知道事件和哪个channel的事件
        SelectionKey sscKey = ssc.register(selector, SelectionKey.OP_ACCEPT, null);
        log.debug("sscKey:{}", sscKey);

        while (true) {
            // 3. select 方法, 没有事件发生，线程阻塞，有事件，线程才会恢复运行
            // select 在事件未处理时，它不会阻塞, 事件发生后要么处理，要么取消，不能置之不理
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                // 处理key 时，要从 selectedKeys 集合中删除，否则下次处理就会有问题
                iterator.remove();
                log.info("key:{}", sk);

                if (sk.isAcceptable()) { // 接收事件
                    ServerSocketChannel channel = (ServerSocketChannel) sk.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);

                    SelectionKey scKey = sc.register(selector, SelectionKey.OP_READ);
                    log.info("scKey:{}", sc);
                } else if (sk.isReadable()) { // 读取事件
                    try {
                        SocketChannel channel = (SocketChannel) sk.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(4);
                        int read = channel.read(buffer);
                        if (-1 == read) {
                            sk.cancel();
                            continue;
                        }

                        buffer.flip();
                        System.out.println(Charset.defaultCharset().decode(buffer));
                    } catch (IOException e) {
                        e.printStackTrace();
                        sk.cancel();
                    }
                }


            }
        }

    }
}
