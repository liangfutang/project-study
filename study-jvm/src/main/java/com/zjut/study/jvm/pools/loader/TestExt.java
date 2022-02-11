package com.zjut.study.jvm.pools.loader;

public class TestExt {
    static {
        System.out.println("自定义类在扩展类加载器中初始化加载");
//        System.out.println("classpath路径下初始化加载");
    }
}
