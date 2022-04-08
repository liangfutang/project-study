package com.zjut.study.thread.completablefuture;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.common.utils.SmallThreadTool;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author jack
 * 创建线程测试
 */
public class _02_CreateThread extends CommonJunitFilter {

    /**
     * 简单的查看线程的运行状态
     *
     * @see Thread.State
     */
    @Test
    public void threadStateTest() throws InterruptedException {
        Thread thread = new Thread();
        System.out.println("1-" + thread.getState());
        thread.start();
        System.out.println("2-" + thread.getState());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("3-" + thread.getState());
    }

    /**
     * Callable的简单使用
     */
    @Test
    public void callableStart() throws ExecutionException, InterruptedException, TimeoutException {
        Callable<String> callable = () -> {
            System.out.println("这个是一个子线程");
            SmallThreadTool.sleep(3 * 1000);
            return "success";
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);

        Thread t = new Thread(futureTask);
        t.start();
        System.out.println("子线程已启动");

        // futureTask会阻塞中主线程知道有效时间内获取到返回值
        String s = futureTask.get(5, TimeUnit.SECONDS);
        System.out.println("获取到的结果:" + s);

        System.out.println("主线程结束");
    }
}
