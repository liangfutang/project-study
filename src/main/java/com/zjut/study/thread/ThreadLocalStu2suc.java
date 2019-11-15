package com.zjut.study.thread;

public class ThreadLocalStu2suc {
    Object lock = new Object();
    public static void main(String[] args) {
        ThreadLocalStu2suc suc = new ThreadLocalStu2suc();
        Product product = suc.new Product();
        Comsum comsum = suc.new Comsum();
        product.start();
        comsum.start();
        System.out.println("结束");
    }

    class Product extends Thread {
        @Override
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

    class Comsum extends Thread {
        @Override
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
