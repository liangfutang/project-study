package com.zjut.study.thread.test.before;

import lombok.Cleanup;

import java.util.concurrent.*;

public class Testaaa {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardOldestPolicy());
        Future<String> submit = executor.submit(new Callable<String>() {
            @Override
            public String call() {
                int i = 1 / 0;
                return "成功的";
            }
        });
        try {
            String s = submit.get();
        } catch (Exception e) {
            System.out.println("线程异常:" + e.getMessage());
        }
        executor.shutdown();
    }
}