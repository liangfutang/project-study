package com.zjut.study.thread.completablefuture;

import com.zjut.study.common.utils.SmallThreadTool;
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
        SmallThreadTool.printTimeAndThread("小明进入饭店");
        SmallThreadTool.printTimeAndThread("小明开始点餐");
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("开始做菜");
            SmallThreadTool.sleep(5000);
            return "鹤顶红做好了";
        }, poolExecutor);
        SmallThreadTool.printTimeAndThread(cf.join());
        SmallThreadTool.printTimeAndThread("小明开干了");
    }
}
