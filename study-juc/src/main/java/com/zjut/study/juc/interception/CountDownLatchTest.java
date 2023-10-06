package com.zjut.study.juc.interception;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            System.out.println("进入到等待1。。。");
            try {
                latch.await();
            } catch (InterruptedException e) {
                System.out.println("等待1失败");
            }
            System.out.println("等待1结束...");
        }).start();

        new Thread(() -> {
            System.out.println("进入到等待2。。。");
            try {
                latch.await();
            } catch (InterruptedException e) {
                System.out.println("等待2失败");
            }
            System.out.println("等待2结束...");
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("睡眠失败");
            }
            System.out.println("进入到通知线程");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("睡眠失败");
            }
            latch.countDown();
            System.out.println("通知线程结束");
        }).start();

        System.out.println("结束");
    }
}
