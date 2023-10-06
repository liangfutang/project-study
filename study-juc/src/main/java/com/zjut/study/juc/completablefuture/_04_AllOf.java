package com.zjut.study.juc.completablefuture;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.common.utils.SmallThreadTool;
import com.zjut.study.juc.completablefuture.entity.Dish;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 *
 */
public class _04_AllOf extends CommonJunitFilter {

    @Test
    public void ofAll() {
        SmallThreadTool.printTimeAndThread("小白和小伙伴们 进餐厅点菜");
        long startTime = System.currentTimeMillis();
//        // 点菜
//        List<Dish> dishList = new ArrayList<>();
//        for (int i=0; i<=10; i++) {
//            Dish dish = new Dish("菜" + i, 1);
//            dishList.add(dish);
//        }
//        // 做菜
//        List<CompletableFuture> cfList = new ArrayList<>();
//        for (Dish dish : dishList) {
//            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> dish.make());
//            cfList.add(future);
//        }
//        // 等待所有任务执行完毕
//        CompletableFuture.allOf(cfList.toArray(new CompletableFuture[cfList.size()])).join();

        // 上述简单写法
        CompletableFuture[] array = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> new Dish("菜" + i, 1))
                .map(dish -> CompletableFuture.runAsync(() -> dish.make()))
                .toArray(size -> new CompletableFuture[size]);

        CompletableFuture.allOf(array).join();

        SmallThreadTool.printTimeAndThread("菜都做好了，上桌，总共花费:" + (System.currentTimeMillis()-startTime));
    }

}
