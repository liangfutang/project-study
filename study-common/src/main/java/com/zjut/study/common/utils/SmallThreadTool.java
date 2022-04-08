package com.zjut.study.common.utils;

import java.util.StringJoiner;

/**
 * 一些简单的线程工具
 * @author jack
 */
public class SmallThreadTool {

    /**
     * 当前线程睡眠 毫秒
     * @param milis
     */
    public static void sleep(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印当前时间戳和线程的基本信息
     * @param tag
     */
    public static void printTimeAndThread (String tag){
        StringJoiner result = new StringJoiner("\t|\t")
                .add(String.valueOf(System.currentTimeMillis()))
                .add(String.valueOf(Thread.currentThread().getId()))
                .add(Thread.currentThread().getName())
                .add(tag);
        System.out.println(result);
    }
}
