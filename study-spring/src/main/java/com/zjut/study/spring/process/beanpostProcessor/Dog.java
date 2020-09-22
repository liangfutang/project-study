package com.zjut.study.spring.process.beanpostProcessor;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog {

    private String name;

    static {
        System.out.println("Dog静态代码块");
    }

    public Dog() {
        System.out.println("Dog构造函数");
    }

    @PostConstruct
    public void init() {
        System.out.println("Dog初始化方法中");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Dog销毁方法中");
    }
}
