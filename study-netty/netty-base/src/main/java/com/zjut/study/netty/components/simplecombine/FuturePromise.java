package com.zjut.study.netty.components.simplecombine;

import com.zjut.study.common.junit.CommonJunitFilter;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * jdk喝netty中的Future喝Promise
 */
@Slf4j
public class FuturePromise extends CommonJunitFilter {

    @Test
    public void test01() throws IOException {
        // 创建事件组
        EventLoopGroup executors = new NioEventLoopGroup(2);  // io事件、普通任务、定时任务
//        EventLoopGroup executors = new DefaultEventLoopGroup();   // 普通任务、定时任务

        // 获取下一个事件循环对象,组内的循环
        System.out.println(executors.next());
        System.out.println(executors.next());
        System.out.println(executors.next()); // 对上一组的循环
        System.out.println(executors.next());

        // 执行普通线程池任务
        executors.next().execute(() -> {
            log.info("eventExec thread...");
        });
        log.info("main thread...");

        // 执行定时线程池任务
        AtomicInteger count = new AtomicInteger();
        executors.next().scheduleAtFixedRate(() -> {
            log.info("定时执行:" + count.getAndIncrement());
        }, 0, 1, TimeUnit.SECONDS);

        System.in.read();
    }

    /**
     * 测试jdk的Future
     */
    @Test
    public void jdkFuture() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<Integer> future = pool.submit(() -> {
            log.info("线程池中执行计算");
            Thread.sleep(3000);
            return 100;
        });

        log.info("等待计算结果");
        log.info("计算结果:{}", future.get());
    }

    /**
     * netty的future
     */
    @Test
    public void nettyFuture() throws ExecutionException, InterruptedException, IOException {
        // 创建执行线程
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        EventLoop eventLoop = eventLoopGroup.next();

        io.netty.util.concurrent.Future<Integer> future = eventLoop.submit(() -> {
            log.info("netty池中执行计算");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 100;
        });

        // 等待计算结果
//        log.info("等待计算结果");
//        log.info("计算结果:{}", future.get());

        // 监听器接受计算结果
        future.addListener(new GenericFutureListener<io.netty.util.concurrent.Future<? super Integer>>() {
            @Override
            public void operationComplete(io.netty.util.concurrent.Future<? super Integer> f) throws Exception {
                log.info("接受计算结果:{}", f.getNow());
            }
        });
        System.in.read();
    }

    /**
     * netty的promise
     */
    @Test
    public void nettyPromise() throws ExecutionException, InterruptedException, IOException {
        EventLoop eventLoop = new NioEventLoopGroup().next();
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(() -> {
            try {
//                int i = 1/0;
                log.info("netty线程池中执行计算");
                Thread.sleep(3000);
                promise.setSuccess(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                promise.setFailure(e);
            }
        }).start();

        // 等待接受结果
//        log.info("等待接受结果");
//        log.info("接受结果:{}", promise.get());

        // 监听器接受结果
        promise.addListener(new GenericFutureListener<io.netty.util.concurrent.Future<? super Integer>>() {
            @Override
            public void operationComplete(io.netty.util.concurrent.Future<? super Integer> f) throws Exception {
                log.info("接受到的结果:{}", f.getNow());
            }
        });
        System.in.read();
    }
}
