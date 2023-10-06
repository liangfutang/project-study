package com.zjut.study.thread.completablefuture;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.common.utils.SmallThreadTool;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class _03_SupplyAsync extends CommonJunitFilter {


    /**
     * 简单的异步测试
     */
    @Test
    public void asyncSimple () throws ExecutionException, InterruptedException {
        SmallThreadTool.printTimeAndThread("小白到进入餐厅");
        SmallThreadTool.printTimeAndThread("小白点了 番茄鸡蛋 + 一碗饭");

        // 两个都是异步的，区别在是否会有返回
//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {});
        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("厨师开始炒菜");
            SmallThreadTool.sleep(200);
            SmallThreadTool.printTimeAndThread("厨师开始打饭");
            SmallThreadTool.sleep(200);
            return "番茄炒蛋 + 米饭 做好了";
        });

        SmallThreadTool.printTimeAndThread("小白在刷抖音");
        SmallThreadTool.printTimeAndThread(String.format("%s, 小白开吃", async.get()));
        // join()方法抛出的是uncheck异常（即未经检查的异常),不会强制开发者抛出，
        //　　   会将异常包装成CompletionException异常 /CancellationException异常，但是本质原因还是代码内存在的真正的异常
        // get()方法抛出的是经过检查的异常，ExecutionException, InterruptedException 需要用户手动处理（抛出或者 try catch）
    }

}
