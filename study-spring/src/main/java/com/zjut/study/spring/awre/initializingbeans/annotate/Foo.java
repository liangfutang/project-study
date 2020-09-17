package com.zjut.study.spring.awre.initializingbeans.annotate;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 测试注解版本的回调
 */
@Component
public class Foo {

    /**
     * 该注解表示一个方法:javax.annotation.PostConstruct,spring容器,在bean创建之后,在初始化之前调用!!
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception{
        System.out.println("annotate 版本的初始化 init method is called");
    }

    /**
     * 该注解表示一个方法:javax.annotation.PreDestory,在初始化之后,在销毁之前!!
     * @throws Exception
     */
    @PreDestroy
    public void destory() throws RuntimeException{
        System.out.println("annotate 版本的销毁 destory method is called");
    }
}
