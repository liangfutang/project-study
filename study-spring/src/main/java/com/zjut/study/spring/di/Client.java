package com.zjut.study.spring.di;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Client {

    @Test
    public void testDi1() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        Object a = annotationConfigApplicationContext.getBean("a");
        System.out.println("输出:" + a);
    }

}
