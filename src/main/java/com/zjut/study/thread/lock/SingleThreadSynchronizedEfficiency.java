package com.zjut.study.thread.lock;

/**
 * 测试单线程和多线程的情况下synchronized对效率的影响
 */
public class SingleThreadSynchronizedEfficiency {
    public static void main(String[] args) {
        SingleThreadSynchronizedEfficiency single = new SingleThreadSynchronizedEfficiency();

        long singleNoSyncStart = System.currentTimeMillis();
        single.singleNoSync();
        long singleNoSyncEnd = System.currentTimeMillis();
        System.out.println("单线程不加锁：" + (singleNoSyncEnd-singleNoSyncStart));

        long singleWithSyncStart = System.currentTimeMillis();
        single.singleWithSync();
        long singleWithSyncEnd = System.currentTimeMillis();
        System.out.println("单线程加锁：" + (singleWithSyncEnd-singleWithSyncStart));

        long multiNoSyncStart = System.currentTimeMillis();
        single.multiNoSync();
        long multiNoSyncEnd = System.currentTimeMillis();
        System.out.println("多线程不加锁：" + (multiNoSyncEnd-multiNoSyncStart));

        long multiWithSyncStart = System.currentTimeMillis();
        single.multiWithSync();
        long multiWithSyncEnd = System.currentTimeMillis();
        System.out.println("多线程加锁：" + (multiWithSyncEnd-multiWithSyncStart));
    }

    /**
     * 单线程不加锁
     */
    int a=0;
    void singleNoSync() {
        new Thread(() -> {
            for (int i=0; i<1000000; i++) {
                a++;
            }
        }).start();
    }

    /**
     * 单线程加锁
     */
    synchronized void singleWithSync() {
        new Thread(() -> {
            for (int i=0; i<1000000; i++) {
                synchronized (this) {
                    a++;
                }
            }
        }).start();
    }

    /**
     * 多线程不加锁
     */
    int b=0;
    void multiNoSync() {
        for (int i=0; i<100; i++) {
            new Thread(()->{
                for (int j=0; j<10000; j++) {
                    b++;
                }
            }).start();
        }
    }

    /**
     * 多线程加锁
     */
    void multiWithSync() {
        for (int i=0; i<100; i++) {
            new Thread(() -> {
                synchronized (this) {
                    for (int j=0; j<10000; j++) {
                        b++;
                    }
                }
            }).start();
        }
    }
}
