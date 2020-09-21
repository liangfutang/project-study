package com.zjut.study.spring.process.beanpostProcessor;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用来测试注解方式的生命周期回调的客户端
 */
@ComponentScan("com.zjut.study.spring.process.beanpostProcessor")
public class Client extends CommonJunitFilter {

    /**
     * 通过类型获取对象，测试结果如下
     *
     * 后置处理器--before, bean:com.zjut.study.spring.process.beanpostProcessor.Client@2af004b,beanNameclient
     * 后置处理器--after, bean:com.zjut.study.spring.process.beanpostProcessor.Client@2af004b,beanNameclient
     * 18:31:25.416 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'dog'
     * 静态代码块
     * 构造函数
     * 后置处理器--before, bean:com.zjut.study.spring.process.beanpostProcessor.Dog@543295b0,beanNamedog
     * 初始化方法中
     * 后置处理器--after, bean:com.zjut.study.spring.process.beanpostProcessor.Dog@543295b0,beanNamedog
     * 18:31:25.441 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@453da22c, started on Mon Sep 21 18:31:25 CST 2020
     * 销毁方法中
     */
    @Test
    public void test1() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Client.class);
        Dog bean = ac.getBean(Dog.class);
        ac.close();
    }

}
