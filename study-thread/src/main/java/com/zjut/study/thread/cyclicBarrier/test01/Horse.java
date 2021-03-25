package com.zjut.study.thread.cyclicBarrier.test01;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

/**
 * 参加跑马的马屁
 */
public class Horse implements Runnable{
    /**
     * 屏障
     */
    private CyclicBarrier cyclicBarrier;
    private static Random rand = new Random(47);

    /**
     * 本马的牌号
     */
    private int num;
    /**
     * 步数
     */
    private int steps = 0;

    public Horse(int num) {
        this.num = num;
    }

    public int getSteps() {
        return steps;
    }

    public int getNum() {
        return num;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    /**
     * 该马的路径
     * @return
     */
    public String getTrace() {
        StringBuilder trace = new StringBuilder();
        for (int i=0 ; i<steps; i++) {
            trace.append("*");
        }
        return trace.toString() + this.num;
    }

    @Override
    public void run() {
        try {
            // 到达终点的时候会终止线程，所有均没到终点前不会挺
            while (!Thread.interrupted()) {
                // 计算本时间段要走的步数
                int i = rand.nextInt(3);
                steps += i;
                // 本次刷新的时间段内计算完步数，开始等待下个时间段的开始
                cyclicBarrier.await();
            }
        } catch (Exception e) {

        }
    }
}
