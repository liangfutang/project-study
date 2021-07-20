package com.zjut.study.thread.completablefuture;

import java.util.StringJoiner;

public class SmallUtil {


    public static void sleep(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printTimeAndThread (String tag){
        StringJoiner result = new StringJoiner("\t|\t")
                .add(String.valueOf(System.currentTimeMillis()))
                .add(String.valueOf(Thread.currentThread().getId()))
                .add(Thread.currentThread().getName())
                .add(tag);
        System.out.println(result);
    }
}
