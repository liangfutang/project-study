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
     * 后置处理器--before, bean:com.zjut.study.spring.process.beanpostProcessor.Client@54422e18,beanName:client
     * 后置处理器--after, bean:com.zjut.study.spring.process.beanpostProcessor.Client@54422e18,beanName:client
     * 20:51:03.662 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'cat'
     * 后置处理器--before, bean:com.zjut.study.spring.process.beanpostProcessor.Cat@117159c0,beanName:cat
     * 后置处理器--after, bean:com.zjut.study.spring.process.beanpostProcessor.Cat@117159c0,beanName:cat
     * 20:51:03.662 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'dog'
     * Dog静态属性中构建
     * Dog静态代码块
     * Dog构造函数
     * 后置处理器--before, bean:com.zjut.study.spring.process.beanpostProcessor.Dog@58cbafc2,beanName:dog
     * 传进前置来的是Dog
     * Dog初始化方法中
     * 后置处理器--after, bean:com.zjut.study.spring.process.beanpostProcessor.Dog@58cbafc2,beanName:dog
     * 20:51:03.685 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@58fdd99, started on Wed Sep 23 20:51:03 CST 2020
     * Dog销毁方法中
     */
    @Test
    public void test1() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Client.class);
        Dog bean = ac.getBean(Dog.class);
        ac.close();
    }

}
