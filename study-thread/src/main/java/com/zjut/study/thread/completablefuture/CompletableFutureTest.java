package com.zjut.study.thread.completablefuture;

import org.junit.Test;

import java.util.concurrent.*;

public class CompletableFutureTest {

    private static ThreadPoolExecutor poolExecutor = null;
    static {
        poolExecutor = new ThreadPoolExecutor(2, 4,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(3)) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                super.beforeExecute(t, r);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
            }
        };
    }


    /**
     * 另起一个线程并行运行
     */
    @Test
    public void test01 () {
        SmallUtil.printTimeAndThread("小明进入饭店");
        SmallUtil.printTimeAndThread("小明开始点餐");
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            SmallUtil.printTimeAndThread("开始做菜");
            SmallUtil.sleep(5000);
            return "鹤顶红做好了";
        }, poolExecutor);
        SmallUtil.printTimeAndThread(cf.join());
        SmallUtil.printTimeAndThread("小明开干了");
    }
}
