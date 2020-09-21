package com.zjut.study.spring.process.beanpostProcessor;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog {

    private String name;

    static {
        System.out.println("静态代码块");
    }

    public Dog() {
        System.out.println("构造函数");
    }

    @PostConstruct
    public void init() {
        System.out.println("初始化方法中");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("销毁方法中");
    }
}
