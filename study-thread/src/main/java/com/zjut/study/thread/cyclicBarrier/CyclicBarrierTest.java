package com.zjut.study.thread.cyclicBarrier;

import com.zjut.study.thread.cyclicBarrier.test01.Horse;
import com.zjut.study.thread.cyclicBarrier.test01.HorseRace;
import org.junit.Test;

public class CyclicBarrierTest {

    @Test
    public void test01() throws InterruptedException {
        // 创建异常比赛
        HorseRace horseRace = new HorseRace();
        // 选七匹马，并编号放到马池
        for (int i=1 ; i<=7 ; i++) {
            System.out.println("选好第" + i + "匹马，放进马池");
            Horse horse = new Horse(i);
            horseRace.add(horse);
        }
        // 开启一个分线程准备开跑
        Thread race = new Thread(() -> {
            horseRace.start(7);
        }, "分线程比赛");
        race.start();

        // 等待比赛的结束
        synchronized (horseRace) {
            horseRace.wait();
        }

        System.out.println("本场比赛结束了");
    }
}
