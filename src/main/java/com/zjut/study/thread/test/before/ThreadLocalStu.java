package com.zjut.study.thread.test.before;

/**
 * @Author:     liangfutang
 * @Description:  
 * @Date:    2019/5/27 16:55
 * @Version:    1.0
 */
public class ThreadLocalStu {

    public static void main(String[] args) {
        final Object lock = new Object();

        new Thread(){
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        System.out.println("开始等待");
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("等待失败");
                    }
                }
                System.out.println("等待结束");
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("进入到notify");
                    synchronized (lock) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            System.out.println("睡眠失败");
                        }
                        lock.notifyAll();
                    }
                    System.out.println("notify结束");
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                super.run();
            }
        }.start();
        System.out.println("结束");
    }

}
