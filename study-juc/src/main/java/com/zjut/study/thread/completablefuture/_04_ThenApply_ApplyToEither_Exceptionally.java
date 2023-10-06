package com.zjut.study.thread.completablefuture;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.common.utils.SmallThreadTool;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 *
 */
public class _04_ThenApply_ApplyToEither_Exceptionally extends CommonJunitFilter {

    /**
     * thenApplyAsync类似于thenCompose
     * 对执行完结果做操作
     */
    @Test
    public void thenApply_thenApplyAsync() {
        SmallThreadTool.printTimeAndThread("小白吃好了");
        SmallThreadTool.printTimeAndThread("小白 结账、要求开发票");

        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("服务员收款 500元");
            SmallThreadTool.sleep(100);
            return "500";
        // thenApply是将上述结果交到下面的Function函数中执行
        // thenApplyAsync是将上述结果异步的交给下面的function函数执行
//        }).thenApply(money -> {
        }).thenApplyAsync(money -> {
            SmallThreadTool.printTimeAndThread(String.format("服务员开发票 面额 %s元", money));
            SmallThreadTool.sleep(200);
            return String.format("%s元发票", money);
        });

        SmallThreadTool.printTimeAndThread("小白 接到朋友的电话，想一起打游戏");
        SmallThreadTool.printTimeAndThread(String.format("小白拿到%s，准备回家", async.join()));
    }

    @Test
    public void applyToEither() {
        SmallThreadTool.printTimeAndThread("张三走出餐厅，来到公交站");
        SmallThreadTool.printTimeAndThread("等待 700路 或者 800路 公交到来");

        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("700路公交正在来的路上");
            SmallThreadTool.sleep(700);
            return "700路";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("800路公交正在来的路上");
            SmallThreadTool.sleep(800);
            return "800路";
        }), firstCome -> firstCome + "到了");

        SmallThreadTool.printTimeAndThread(String.format("%s,小白坐车回家", async.join()));
    }

    /**
     * 简单的异常测试
     */
    @Test
    public void exceptionally() {
        SmallThreadTool.printTimeAndThread("小白走出餐厅，来到公交站");
        SmallThreadTool.printTimeAndThread("等待 700路 或者 800路 公交到来");

        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("700路正在过来");
            SmallThreadTool.sleep(700);
            return "700路";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            SmallThreadTool.printTimeAndThread("800路正在过来");
            SmallThreadTool.sleep(800);
            return "800路";
        }), firstCome -> {
            firstCome += "到了";
            if (firstCome.startsWith("700")) {
                throw new RuntimeException("700路撞树了...");
            }
            return firstCome;
        }).exceptionally(e -> {
            SmallThreadTool.printTimeAndThread(e.getMessage());
            SmallThreadTool.printTimeAndThread("小白开始叫出租车");
            return "出租车 叫到了";
        });

        SmallThreadTool.printTimeAndThread(String.format("%s,小白坐车回家", async.join()));
    }
}
