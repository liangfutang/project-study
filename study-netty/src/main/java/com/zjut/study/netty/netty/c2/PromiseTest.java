package com.zjut.study.netty.netty.c2;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * 对Promise的相关测试
 */
@Slf4j
public class PromiseTest {

    /**
     * netty promise的两种使用测试
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testNettyPromise01() throws ExecutionException, InterruptedException {
        EventLoop eventLoop = new NioEventLoopGroup().next();
        // 可以主动创建 promise, 结果容器
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

//        log.info("等待接收结果");
//        log.info("接收到结果:{}", promise.get());

        promise.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.info("接收到的结果:{}", future.getNow());
            }
        });
    }
}
