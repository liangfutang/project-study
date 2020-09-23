package com.zjut.study.spring.aop.simpleuse;

import org.springframework.stereotype.Component;

@Component
public class PeopleService {

    public void eat(String food) {
        System.out.println("小明爱吃" + food);
    }

    public void run(String ground) {
        System.out.println("小明喜欢在" + ground + "跑步");
    }
}
