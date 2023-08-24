package com.zjut.study.nio.server.write;

import com.zjut.study.nio.utils.ByteBufferUtil;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程版本
 * @author tlf
 */
public class MultiThreadWriteServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        Selector boss = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8080));
        ssc.register(boss, SelectionKey.OP_ACCEPT);

        // 创建多个工作线程
        Worker[] workers = new Worker[Runtime.getRuntime().availableProcessors()];
        for (int i=0; i< workers.length; i++) {
            workers[i] = new Worker("worker - " + i);
        }

        AtomicInteger count = new AtomicInteger();
        while (true) {
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                // 当有连接的时候分配合适的工作线程来处理任务
                if (key.isAcceptable()) {
                    SocketChannel channel = ssc.accept();
                    System.out.println("建立连接:" + channel.getRemoteAddress());
                    channel.configureBlocking(false);
                    System.out.println("注册前");
                    workers[count.incrementAndGet() % workers.length].register(channel);
                    System.out.println("注册后");
                }
            }
        }
    }

    static class Worker implements Runnable{
        private String workName;
        private Thread thread;
        private Selector selector;
        // 如果任务过多，将任务存储到队列中慢慢执行
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();
        // 当前工作线程是不是已经启动了
        private volatile boolean start = false;

        public Worker(String name) {
            this.workName = name;
        }

        public void register(SocketChannel channel) throws IOException {
            if (!start) {
                selector = Selector.open();
                thread = new Thread(this, workName);
                thread.start();
                start = true;
            }
            selector.wakeup();
            channel.register(selector, SelectionKey.OP_READ);
        }

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.read(buffer);
                        buffer.flip();
                        ByteBufferUtil.debugAll(buffer);
                    }
                }
            }
        }
    }
}
