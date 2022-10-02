package com.zjut.study.spring.cycledependency.simplejavacycle.initcycle;

import lombok.Getter;
import lombok.Setter;

public class Cat {
    @Getter
    private String name = "小喵";
    @Setter
    private Dog dog;

    public void getDogName() {
        System.out.println("依赖方名叫:" + dog.getName());
    }
}
