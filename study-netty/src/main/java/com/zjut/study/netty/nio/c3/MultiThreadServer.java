package com.zjut.study.netty.nio.c3;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import static com.zjut.study.netty.ByteBufferUtil.debugAll;

/**
 * 多线程服务器
 */
@Slf4j
public class MultiThreadServer {

    @Test
    public void test01() throws IOException {
        Selector boss = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8081));
        SelectionKey bossKey = ssc.register(boss, SelectionKey.OP_ACCEPT);
        // 创建合适的工作 worker 并初始化
        Worker[] workers = new Worker[Runtime.getRuntime().availableProcessors()];
        for (int i=0; i< workers.length; i++) {
            workers[i] = new Worker("work-" + i);
        }

        AtomicInteger count = new AtomicInteger();
        while (true) {
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();  // 接收客户端的连接请求
                    sc.configureBlocking(false);
                    log.info("接收到一个连接请求；{}", sc);
                    // 使用简单的负载均衡从现有的工作者worker中拿出一个执行接收任务
                    workers[count.getAndIncrement() % workers.length].register(sc);
                }
            }
        }
    }

    static class Worker implements Runnable{

        private Thread thread;
        private String name;
        private volatile boolean start = false;  // 还未初始化
        private Selector selector;

        public Worker(String name) {
            this.name = name;
        }

        /**
         * worker 的注册
         */
        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                selector = Selector.open();  // 新开的一个，和上面的boss不是同一个
                thread = new Thread(this, name);
                thread.start();
                start = true;
            }
            selector.wakeup(); // 叫醒被阻塞的select()，不区分执行的前后顺序
            sc.register(selector, SelectionKey.OP_READ);  // 接收到一个客户端的连接后，将其注册到select上，然后交给子线程去处理后续的工作
        }

        @Override
        public void run() {
            while (true) {
                try {
                    log.info("即将阻塞等待");
                    selector.select();   // worker-0  阻塞
                    log.info("阻塞等待结束");
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        log.info("处理key:{}", key);

                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);

                            SocketChannel channel = (SocketChannel) key.channel();
                            log.info("read... " + channel.getRemoteAddress());
                            channel.read(buffer);
                            buffer.flip();
                            debugAll(buffer);
                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
