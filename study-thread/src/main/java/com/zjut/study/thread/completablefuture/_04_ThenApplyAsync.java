package com.zjut.study.thread.completablefuture;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.common.utils.SmallThreadTool;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 *
 */
public class _04_ThenApplyAsync extends CommonJunitFilter {

    /**
     * thenApplyAsync类似于thenCompose
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
}
