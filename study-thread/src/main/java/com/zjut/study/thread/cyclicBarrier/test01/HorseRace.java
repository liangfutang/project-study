package com.zjut.study.thread.cyclicBarrier.test01;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 跑马场
 */
public class HorseRace {
    /**
     * 跑道长度
     */
    private final int MAX_LENGTH = 50;
    /**
     * 存储马
     */
    private List<Horse> horses = new LinkedList<>();
    /**
     * 跑道
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(7);

    public void add(Horse horse) {
        horses.add(horse);
    }


    public void start(int parties) {
        /**
         * 创建栅栏和跑道，准备跑马刷屏过程
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties, () ->{
            // 清屏命令
//            try {
////                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//            } catch (Exception e) {
//                System.out.println("清屏异常");
//            }
            // 打印边界
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0 ; i<MAX_LENGTH ; i++)  {
                stringBuilder.append("=");
            }
            System.out.println(stringBuilder.toString());

            // 所有的马都在同一个时间点上了，就开始跑本次这个时间段
            for (Horse horse : horses) {
                executorService.execute(horse);
            }

            // 打印每一匹马当前的路径
            for (Horse horse : horses) {
                System.out.println(horse.getTrace());
            }

            // 判断有没有到终点的冠军
            for (Horse horse : horses) {
                if (horse.getSteps() >= MAX_LENGTH) {
                    try {
                        executorService.shutdownNow();
                    } catch (Exception e) {
                    }
                    // 所有的都结束了，唤醒等待的主线程
                    synchronized (this) {
                        this.notifyAll();
                    }

                    System.out.println("本次冠军马是:" + horse.getNum());
                    return;
                }
            }

            // 刷新一段时间后再次开始跑
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 将马儿一个个的放到起跑线，并开出发令枪
        for (Horse horse : horses) {
            horse.setCyclicBarrier(cyclicBarrier);
            executorService.execute(horse);
        }
    }

}
