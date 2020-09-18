package com.zjut.study.spring.di.simple;

import org.springframework.stereotype.Component;

@Component
public class A {

    public A(B b) {
        System.out.println("输出:" + b);
        System.out.println("构建A Constructor");
    }

}
