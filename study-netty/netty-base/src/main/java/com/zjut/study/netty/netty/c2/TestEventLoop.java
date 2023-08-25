package com.zjut.study.netty.netty.c2;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 事件组 EventLoopGroup 的基本功能测试
 */
@Slf4j
public class TestEventLoop {

    @Test
    public void test01() throws IOException {
        // 1. 创建事件组
        EventLoopGroup eventExecutors = new NioEventLoopGroup(2);  // io事件、普通任务、定时任务
//        EventLoopGroup eventExecutors = new DefaultEventLoopGroup(2);   // 普通任务、定时任务

        // 2. 获取下一个事件循环对象
        System.out.println(eventExecutors.next());
        System.out.println(eventExecutors.next());
        System.out.println(eventExecutors.next()); // 对上一组的循环
        System.out.println(eventExecutors.next());

        // 3. 执行普通线程池任务
        eventExecutors.next().execute(() -> {
            log.info("eventExec thread...");
        });
        log.info("main thread...");

        // 4. 执行定时线程池任务
        AtomicInteger i = new AtomicInteger();
        eventExecutors.next().scheduleAtFixedRate(() -> {
            log.info("定时执行:" + i.getAndIncrement());
        }, 0, 1, TimeUnit.SECONDS);

        System.in.read();
    }
}
