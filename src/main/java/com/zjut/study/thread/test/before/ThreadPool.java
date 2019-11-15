package com.zjut.study.thread.test.before;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.*;

public class ThreadPool {
    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//       //final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, );
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();
//        Test test = new Test();
//        test.setA("aaaa").setB("ss").setC(23);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        Future<?> submit = executor.submit(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int a = 1/0;
            }
        });
        Future<?> submit1 = executor.submit(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程2正常打印");
            }
        });
        Future<?> submit2 = executor.submit(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程3正常打印");
            }
        });
        executor.shutdown();
        System.out.println("主线程结束");
    }
}

@Data
@Accessors(chain = true)
class Test {

    private String a;

    private String b;

    private Integer c;
}
