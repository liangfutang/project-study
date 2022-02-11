package com.zjut.study.jvm.pools.loader;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HelloWord1 {
    static {
        System.out.println("main 初始化");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        String format = String.format("%02d", 5);//25为int型
        System.out.println(format);
//        Integer a = 16;
//        System.out.println(String.valueOf(a));
        // 1. 静态爱常量不会触发初始化
//        System.out.println(B.b1);
        // 2. 类对象.class不会触发初始化
//        System.out.println(B.class);
        // 3. 创建数组不会初始化
//        System.out.println(new B[0]);
        // 4. 不会初始化B，但是会将B和A记载进来
//        ClassLoader classLoader = HelloWord1.class.getClassLoader();
//        classLoader.loadClass("com.zjut.study.jvm.pools.loader.B");
        // 5. 同4
//        ClassLoader classLoader1 = HelloWord1.class.getClassLoader();
//        Class.forName("com.zjut.study.jvm.pools.loader.B", false, classLoader1);

        // 1. 首次访问这个类的静态变量或静态方法
//        System.out.println(A.a);
        // 2.子类初始化，如果父类还没初始化，会引发
//        System.out.println(B.b2);
        // 3. 子类访问父类静衣变量，只会出发父类初始化
//        System.out.println(B.a);
        // 4. 会初始化B，并且会先初始化A
//        Class.forName("com.zjut.study.jvm.pools.loader.B");
//        A a1 = new A();
//        a1.setId(1);
//        a1.setName("jack");
//        A a2 = new A();
//        a2.setId(1);
//        a2.setName("rose");
//        A a3 = new A();
//        a3.setId(2);
//        a3.setName("mike");
//
//        List<A> list = new ArrayList<>();
//        list.add(a1);
//        list.add(a2);
//        list.add(a3);
//
//        Map<Integer, A> collect = list.stream().collect(Collectors.toMap(A::getId, Function.identity(), (one, two) -> two));
//        System.out.println(collect);
    }

}

@Data
class A {
    private int id;
    private String name;

    static int a = 0;
    static {
        System.out.println("A 初始化");
    }
}
class B extends A {
    final static double b1 = 5.0;
    static boolean b2 = false;
    static {
        System.out.println("B 初始化");
    }
}
