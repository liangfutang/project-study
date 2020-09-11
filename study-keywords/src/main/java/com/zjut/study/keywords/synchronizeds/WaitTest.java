package com.zjut.study.keywords.synchronizeds;

import org.junit.jupiter.api.Test;

public class WaitTest {

    @Test
    public void test1() {
        Object lock = new Object();

        new Thread( ()-> {
            System.out.println("进入唤醒线程:" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                System.out.println("开始即将唤醒了.....");
                lock.notifyAll();
            }
            System.out.println("唤醒结束结束");
        }, "唤醒线程").start();

        new Thread( ()-> {
            System.out.println("进入睡眠线程:" + Thread.currentThread().getName());
            synchronized (lock) {
                System.out.println("开始即将睡眠=====");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("睡眠结束");
        }, "睡眠线程").start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
