package com.zjut.study.spring.cycledependency.simplespringcycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Dog {

    @Autowired
    private Cat cat;

    public Dog() {
        System.out.println("新建Dog");
    }

    public void testDog() {
        System.out.println("测试中的dog");
    }

    public void testCatAutowired() {
        cat.testCat();
    }
}
