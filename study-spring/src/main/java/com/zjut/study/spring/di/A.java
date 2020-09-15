package com.zjut.study.spring.di;

import org.springframework.stereotype.Component;

@Component
public class A {

    public A() {
        System.out.println("构建A Constructor");
    }

}
