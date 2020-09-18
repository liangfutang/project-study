package com.zjut.study.spring.awre.beanaware;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class Bread {

    public Bread() {
        System.out.println("构建Bread....");
    }

    private String name;

    public String eatWay(String way) {
        return StringUtils.isEmpty(way) ? "屁都没的吃" : way;
    }
}
