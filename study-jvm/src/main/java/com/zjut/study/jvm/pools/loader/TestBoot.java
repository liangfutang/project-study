package com.zjut.study.jvm.pools.loader;

import java.sql.DriverManager;
import java.util.ServiceLoader;

public class TestBoot {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
//        Class<?> aClass = Class.forName("com.zjut.study.jvm.pools.loader.Cat");
//        System.out.println(aClass.getClassLoader());    // 返回内容   hi  \n  sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(DriverManager.class.getClassLoader());
    }
}

class Cat {
    static {
        System.out.println("hi");
    }
}
