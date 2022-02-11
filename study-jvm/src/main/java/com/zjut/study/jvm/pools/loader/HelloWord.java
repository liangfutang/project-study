package com.zjut.study.jvm.pools.loader;

import java.io.IOException;

public class HelloWord {
    static int a;
    static int b = 21;
    static final int c = 22;
    static final String d = "hello";
    static final Integer e = 12;

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = HelloWord.class.getClassLoader();
//        // ClassLoader不会导致类的解析和初始化
        Class<?> aClass = classLoader.loadClass("com.zjut.study.jvm.pools.loader.People");

        // new 会出发解析和初始化
//        People people = new People();

        System.in.read();

    }
}

class People {
    Dog dog = new Dog();
}
class Dog {}
