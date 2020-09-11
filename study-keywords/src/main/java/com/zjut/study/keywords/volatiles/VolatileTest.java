package com.zjut.study.keywords.volatiles;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

@Log
public class VolatileTest {

    @Test
    void noVolatile() {
        System.out.println("测试");
    }

    static volatile int count = 0;
    @Test
    void noThreadTest() {

        for(int i=0; i<10000; i++) {
            new Thread(
                    () -> {
                        count++;
                    }
            ).start();
        }
        System.out.println("累加后的count:" + count);
    }

    boolean a = false;
    /**
     * 用作测试加关键字后对线程的影响
     */
    @Test
    void testVolatile() {
        // 用作修改标志位状态的线程
        new Thread(
                () -> {
                    log.info("进入到当前线程，开始睡眠....");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        log.info("睡眠异常");
                        e.printStackTrace();
                    }
                    log.info("睡眠结束...准备干活啦");
                    a = true;
                    System.out.println("结束啦");
                }
        ).start();

        // 用作根据当前标志位状态做事的线程，，读取的是线程内的变量，主线程变化后不知道
        new Thread(
                () -> {
                    log.info("进入到业务线程中....");
                    while (a) {
                        System.out.println("终于是攻破进来了");
                    }
                    log.info("业务线程结束");
                }
        ).start();

        // 这里读取的是主内存中的变量，所以主线程中的值变化后就第一时间获取到了
        while (true){
            log.info("当前标志位状态:" + a);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
