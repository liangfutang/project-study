package com.zjut.study;

import java.lang.instrument.Instrumentation;

/**
 * 打印一个日志
 * @author tlf
 * 下面两个入口方法同时存在的时候，优先取参数多的
 */
public class SimlePrint {

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("hello world, i am agent!");
    }

    public static void premain(String args) {
        System.out.println("hello world!");
    }
}
