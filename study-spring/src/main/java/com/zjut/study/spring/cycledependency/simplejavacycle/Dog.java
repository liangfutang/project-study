package com.zjut.study.spring.cycledependency.simplejavacycle;

import lombok.Getter;
import lombok.Setter;

public class Dog {
    @Getter
    private String name = "小汪";
    @Setter
    private Cat cat;

    public void getCatName() {
        System.out.println("依赖方叫:" + cat.getName());
    }
}
