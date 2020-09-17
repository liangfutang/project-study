package com.zjut.study.spring.di.simple;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ComponentScan("com.zjut.study.spring.di.simple")
@ImportResource("classpath:di/spring.xml")
public class Client extends CommonJunitFilter {

    /**
     * 1. 当AnnotationConfigApplicationContext中不传参数时，只是扫描了，没有实例化，此时不会打印出构造函数中的日志
     */
    @Test
    public void testDi1() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Client.class);
        A a = (A) annotationConfigApplicationContext.getBean("a");
        B b = (B) annotationConfigApplicationContext.getBean("b");
        System.out.println("输出:" + a +" " + b);
    }

}
