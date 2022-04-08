package com.zjut.study.thread.completablefuture;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author jack
 * 创建线程测试
 */
public class _02_CreateThread {

    /**
     * @see Thread.State
     */
    @Test
    public void threadStateTest() throws InterruptedException {
        Thread thread = new Thread();
        System.out.println("1-" + thread.getState());
        thread.start();
        System.out.println("2-" + thread.getState());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("3-" + thread.getState());
    }

    @Test
    public void createThreadRun() {
        Thread thread = new Thread(() -> System.out.println("我是子线程:" + Thread.currentThread().getName()));
        thread.start();
        System.out.println("主线程结束:" + Thread.currentThread().getName());
    }
}
