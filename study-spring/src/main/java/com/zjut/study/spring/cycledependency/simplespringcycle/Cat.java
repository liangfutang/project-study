package com.zjut.study.spring.cycledependency.simplespringcycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component()
public class Cat {

    @Autowired
    private Dog dog;

    public Cat() {
        System.out.println("新建Cat");
    }

    public void testCat() {
        System.out.println("测试中的Cat");
    }

    public void testDogAutowired() {
        dog.testDog();
    }
}
