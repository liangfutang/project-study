package com.zjut.study.dynamic.attach;

import com.zjut.study.model.Fruit;

import java.util.concurrent.TimeUnit;

/**
 * 目标服务
 * 运行时修改
 */
public class DynamicRetransformTarget {

    public static void main(String[] args) throws InterruptedException {
        while(true){
            new Fruit().getFruit();
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
