package com.zjut.study.keywords.lock;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    @Test
    public void test01() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Condition condition1 = lock.newCondition();
        Thread thread = new Thread(() -> {
            lock.lock();
            System.out.println("A拿到锁");
            try {
                condition.await();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("A睡眠异常");
            }
            System.out.println("A睡眠结束");
            lock.unlock();
            System.out.println("A从锁出来了");
        });


        Thread thread1 = new Thread(() -> {
            lock.lock();
            System.out.println("B拿到锁");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("B睡眠异常");
            }
            condition.signalAll();
            System.out.println("B睡眠结束");
            lock.unlock();
            System.out.println("B从锁出来了");
        });

        thread.start();
        thread1.start();

        thread.join();
        thread1.join();
    }
}
