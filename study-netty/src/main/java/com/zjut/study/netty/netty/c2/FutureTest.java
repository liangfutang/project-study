package com.zjut.study.netty.netty.c2;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * jdk netty等Future相关
 */
@Slf4j
public class FutureTest {

    /**
     * 测试jdk的Future
     */
    @Test
    public void testJdk01() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Integer> submit = executor.submit(() -> {
            log.info("线程池中执行计算");
            Thread.sleep(3000);
            return 100;
        });

        log.info("等待计算结果");
        log.info("计算结果:{}", submit.get());
    }

    /**
     * netty对线程池中计算结果等待的测试
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testNetty02() throws ExecutionException, InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        EventLoop eventLoop = eventLoopGroup.next();

        io.netty.util.concurrent.Future<Integer> future = eventLoop.submit(() -> {
            log.info("netty池中执行计算");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });

        // 方式一 等待计算结构
//        log.info("等待计算结果");
//        log.info("计算结果:{}", future.get());

        // 方式二 等待计算结果, 如果使用main方法，整个线程会阻塞守护在此处
        future.addListener(new GenericFutureListener<io.netty.util.concurrent.Future<? super Integer>>() {
            @Override
            public void operationComplete(io.netty.util.concurrent.Future<? super Integer> future) throws Exception {
                log.info("接收计算结果:{}", future.getNow());
            }
        });
    }

}
