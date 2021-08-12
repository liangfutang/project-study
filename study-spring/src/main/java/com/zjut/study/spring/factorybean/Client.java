package com.zjut.study.spring.factorybean;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.spring.awre.beanaware.Food;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.MDC;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.FutureTask;

/**
 * 用来测试注解方式的生命周期回调的客户端
 */
@Slf4j
@ComponentScan("com.zjut.study.spring.factorybean")
public class Client extends CommonJunitFilter {

    /**
     * 通过类型获取对象
     */
    @Test
    public void test1() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Client.class);
        TargetClass targetClass = (TargetClass) ac.getBean("factoryBeanService");
//        TargetClass targetClass1 = (TargetClass) ac.getBean("targetClass");
        // 此步不调的话不会触发@PreDestroy的销毁回调
        System.out.println("输出FactoryBean注入的bean的name参数:" + targetClass.getName());
        ac.close();
    }

}
