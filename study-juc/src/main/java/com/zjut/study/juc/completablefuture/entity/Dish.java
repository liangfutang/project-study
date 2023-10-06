package com.zjut.study.juc.completablefuture.entity;

import com.zjut.study.common.utils.SmallThreadTool;

import java.util.concurrent.TimeUnit;

/**
 * 菜
 */
public class Dish {
    // 菜名
    private String name;
    // 制作时长 (秒)
    private Integer productionTime;

    public Dish(String name, Integer productionTime) {
        this.name = name;
        this.productionTime = productionTime;
    }

    // 做菜
    public void make() {
        SmallThreadTool.sleep(TimeUnit.SECONDS.toMillis(this.productionTime));
        SmallThreadTool.printTimeAndThread(this.name + " 制作完毕，来吃我吧");
    }

    // 做菜
    public void makeUseCPU() {
        SmallThreadTool.printTimeAndThread(this.name + " 制作完毕，来吃我吧" + compute());
    }

    /**
     * 用来模拟 1秒钟的耗时操作
     * 如果你的电脑比较强，可以增大循环次数，否则，需要减少循环次数
     */
    private static long compute() {
        long startTime = System.currentTimeMillis();
        long result = 0;
        // 只是用来模拟耗时操作，没有任何意义
        for (int i = 0; i < Integer.MAX_VALUE / 3; i++) {
            result += i * i % 3;
        }
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        System.out.println(compute());
    }


}
