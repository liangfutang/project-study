package com.zjut.study.spring.awre.beanaware;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用来测试注解方式的生命周期回调的客户端
 */
@ComponentScan("com.zjut.study.spring.awre.beanaware")
public class Client extends CommonJunitFilter {

    /**
     * 用来测试
     */
    @Test
    public void test1() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Client.class);
        Food food = (Food) ac.getBean("food");
        food.eat("大便");
        // 此步不调的话不会触发@PreDestroy的销毁回调
        ac.close();
    }
}
