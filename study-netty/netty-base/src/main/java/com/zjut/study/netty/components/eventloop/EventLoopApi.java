package com.zjut.study.netty.components.eventloop;

import com.zjut.study.common.junit.CommonJunitFilter;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * EventLoop 的 api 使用
 */
@Slf4j
public class EventLoopApi extends CommonJunitFilter {

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
}
