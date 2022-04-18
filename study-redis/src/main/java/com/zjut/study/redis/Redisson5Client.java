package com.zjut.study.redis;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.common.utils.SmallThreadTool;
import com.zjut.study.redis.entity.RedisProperties;
import com.zjut.study.redis.util.RedissionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * Redisson使用案例
 */
public class Redisson5Client extends CommonJunitFilter {

    @Before
    public void init() {
        RedissonClient redissonClient = RedissionUtils.initInstance(new RedisProperties());
    }

    @After
    public void finall() {
        RedissionUtils.closeRedisson();
    }

    @Test
    public void lock() throws IOException, InterruptedException {
        String key = "redission:lock:client";
        RLock lock = RedissionUtils.getRLock(key);
        // 控制多个线程去抢一把锁
        CountDownLatch lockLatch = new CountDownLatch(1);
        // 控制主线程等待子线程结束
        CountDownLatch mainLatch = new CountDownLatch(10);
        IntStream.rangeClosed(1,10).forEach(i -> new Thread(() -> {
            // 等大家都到位了再开始抢
            try {
                lockLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            System.out.printf("第%d个线程拿到了锁%n", i);
            SmallThreadTool.sleep(1000);
            System.out.printf("第%d个线程拿到锁后执行完毕--%n", i);
            lock.unlock();
            // 等所有子线程执行完
            mainLatch.countDown();
        }).start());
        // 十个线程同时开始抢锁
        lockLatch.countDown();

        // 所有子线程执行完再结束主线程
        mainLatch.await();
        System.out.println("主线程结束");
    }
}
