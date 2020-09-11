package com.zjut.study.thread.threadlocal;

public class ThreadLocalStu2 {
    Object lock = new Object();
    public static void main(String[] args) {
        ThreadLocalStu2 stu2 = new ThreadLocalStu2();
        Product product = stu2.new Product();
        Consum consum = stu2.new Consum();
        // 需要对相应的线程做封装
        new Thread(product).start();
        new Thread(consum).start();
        System.out.println("结束");
    }

    class Product implements Runnable{
        public void run() {
            System.out.println("开始生产");
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println("等待失败");
                }
            }
            System.out.println("生产结束");
        }
    }

    class Consum implements Runnable{
        public void run() {
            System.out.println("开始消费");
            synchronized (lock) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("睡眠失败");
                }
                lock.notifyAll();
            }
            System.out.println("消费结束");
        }
    }
}
