package com.zjut.study.juc.completablefuture;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.common.utils.SmallThreadTool;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * 两个异步线程并行和串行，可根据线程执行结果做相关操作
 */
public class _03_ThenCompose_ThenCombine extends CommonJunitFilter {

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
        }).thenCompose(dish -> {
            SmallThreadTool.printTimeAndThread("中间干了点杂活");
            // thenCompose: 在return之前的部分和上面的在同一个线程
            // thenComposeAsync: 在return之前是另一个线程执行，return的内容是第三个不相干的线程执行的
            return CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("厨师开始打饭");
            SmallThreadTool.sleep(200);
            return dish + " + 米饭做好了";
        });});

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
