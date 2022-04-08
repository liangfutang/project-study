package com.zjut.study.thread.completablefuture;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.common.utils.SmallThreadTool;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class _03_CompletableFuture_start extends CommonJunitFilter {


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

    /**
     * 使用内部嵌套的方式将两个线程前后连接组装起来，异步内部再起一个异步
     */
    @Test
    public void thenCompose01() {
        SmallThreadTool.printTimeAndThread("小白到进入餐厅");
        SmallThreadTool.printTimeAndThread("小白点了 番茄鸡蛋 + 一碗饭");

        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("厨师开始炒菜");
            SmallThreadTool.sleep(200);

            CompletableFuture<String> innerFuture = CompletableFuture.supplyAsync(() -> {
                SmallThreadTool.printTimeAndThread("服务员开始打饭");
                SmallThreadTool.sleep(200);
                return " + 米饭";
            });
            return "番茄炒蛋" + innerFuture.join() + "做好了";
        });

        SmallThreadTool.printTimeAndThread("小白在刷抖音");
        SmallThreadTool.printTimeAndThread(String.format("%s, 小白开吃", async.join()));
    }

    /**
     * thenCompose的作用是把前一个任务的结果交个下一个异步任务
     *
     * 使用thenCompos代替内部再起一个线程 起到连接两个线程的作用
     */
    @Test
    public void thenCompose02() {
        SmallThreadTool.printTimeAndThread("小白到进入餐厅");
        SmallThreadTool.printTimeAndThread("小白点了 番茄鸡蛋 + 一碗饭");

        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("厨师开始炒菜");
            SmallThreadTool.sleep(200);
            return "番茄鸡蛋";
        }).thenCompose(dish -> CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("厨师开始打饭");
            SmallThreadTool.sleep(200);
            return dish + " + 米饭做好了";
        }));

        SmallThreadTool.printTimeAndThread("小白在刷抖音");
        SmallThreadTool.printTimeAndThread(String.format("%s, 小白开吃", async.join()));
    }


    /**
     * 简单的并行执行两个线程，然后合并两个线程的执行结果
     */
    @Test
    public void thenCombine01() {
        SmallThreadTool.printTimeAndThread("小白到进入餐厅");
        SmallThreadTool.printTimeAndThread("小白点了 番茄鸡蛋 + 一碗饭");

        CompletableFuture<String> async1 = CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("厨师开始炒菜");
            SmallThreadTool.sleep(2000);
            return "番茄鸡蛋";
        });

        CompletableFuture<String> async2 = CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("服务员开始煮饭");
            SmallThreadTool.sleep(2000);
            return "米饭";
        });
        SmallThreadTool.printTimeAndThread("小白在刷抖音");

        String result = String.format("%s + %s 做好了", async1.join(), async2.join());

        SmallThreadTool.printTimeAndThread("是服务员开始打饭");
        SmallThreadTool.sleep(200);

        SmallThreadTool.printTimeAndThread(String.format("%s, 小白开吃", result));
    }


    /**
     * 将两个并行执行的线程的结果执行合并操作
     */
    @Test
    public void thenCombine02() {
        SmallThreadTool.printTimeAndThread("小白到进入餐厅");
        SmallThreadTool.printTimeAndThread("小白点了 番茄鸡蛋 + 一碗饭");

        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("厨师开始炒菜");
            SmallThreadTool.sleep(200);
            return "番茄炒鸡蛋";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("服务员开始煮饭");
            SmallThreadTool.sleep(200);
            return "米饭";
        }), (dish, rice) -> {
            SmallThreadTool.printTimeAndThread("服务员开始打饭");
            SmallThreadTool.sleep(200);
            return String.format("%s + %s 做好了", dish, rice);
        });

        SmallThreadTool.printTimeAndThread("小白在刷抖音");
        SmallThreadTool.printTimeAndThread(String.format("%s, 小白开吃", async.join()));
    }
}
