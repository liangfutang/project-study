package com.zjut.study.nio.server.thread;

import com.zjut.study.nio.utils.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 客户端和服务的连接
 * @author jack
 */
@Slf4j
public class CS01 {

    /**
     * 阻塞式
     *
     * 阻塞模式下，相关方法都会导致线程暂停
     *      ServerSocketChannel.accept 会在没有连接建立时让线程暂停
     *      SocketChannel.read 会在没有数据可读时让线程暂停
     * 阻塞的表现其实就是线程暂停了，暂停期间不会占用 cpu，但线程相当于闲置
     * 单线程下，阻塞方法之间相互影响，几乎不能正常工作，需要多线程支持
     * 但多线程下，有新的问题，体现在以下方面
     *      32 位 jvm 一个线程 320k，64 位 jvm 一个线程 1024k，如果连接数过多，必然导致 OOM，并且线程太多，反而会因为频繁上下文切换导致性能降低
     *      可以采用线程池技术来减少线程数和线程上下文切换，但治标不治本，如果有很多连接建立，但长时间 inactive，会阻塞线程池中所有线程，因此不适合长连接，只适合短连接
     * @throws IOException
     */
    @Test
    public void blockServer() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 创建服务并绑定端口
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));

        // 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // accept 建立与客户端连接， SocketChannel 用来与客户端之间通信
            log.debug("connecting...");
            // // 阻塞方法，线程停止运行
            SocketChannel sc = ssc.accept();
            log.debug("connected... {}", sc);
            channels.add(sc);

            for (SocketChannel channel : channels) {
                // 接收客户端发送的数据
                log.debug("before read... {}", channel);
                // 阻塞方法，线程停止运行
                channel.read(buffer);
                buffer.flip();
                ByteBufferUtil.debugAll(buffer);
                buffer.clear();
                log.debug("after read...{}", channel);
            }
        }
    }

    /**
     * 非阻塞式
     *
     * 非阻塞模式下，相关方法都会不会让线程暂停
     *      在 ServerSocketChannel.accept 在没有连接建立时，会返回 null，继续运行
     *      SocketChannel.read 在没有数据可读时，会返回 0，但线程不必阻塞，可以去执行其它 SocketChannel 的 read 或是去执行 ServerSocketChannel.accept
     *      写数据时，线程只是等待数据写入 Channel 即可，无需等 Channel 通过网络把数据发送出去
     * 但非阻塞模式下，即使没有连接建立，和可读数据，线程仍然在不断运行，白白浪费了 cpu
     * 数据复制过程中，线程实际还是阻塞的（AIO 改进的地方）
     */
    @Test
    public void nonBlockServer() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);

        // 创建服务端绑定端口并设置非阻塞式
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);

        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            SocketChannel sc = ssc.accept();
            if (Objects.nonNull(sc)) {
                log.debug("connected... {}", sc);
                sc.configureBlocking(false);
                channels.add(sc);
            }

            for (SocketChannel channel : channels) {
                int read = channel.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    ByteBufferUtil.debugAll(buffer);
                    buffer.clear();
                    log.debug("after read...{}", channel);
                }
            }
        }
    }


    @Test
    public void client() throws IOException {
        SocketChannel open = SocketChannel.open();
        open.connect(new InetSocketAddress(8080));
        System.out.println("waiting...");
    }

}
