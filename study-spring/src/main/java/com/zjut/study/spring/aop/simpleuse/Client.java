package com.zjut.study.spring.aop.simpleuse;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan("com.zjut.study.spring.aop.simpleuse")
@EnableAspectJAutoProxy
public class Client extends CommonJunitFilter {

    private AnnotationConfigApplicationContext ac;

    @Before
    public void beforeInner() {
        System.out.println("子类中的before");
        ac  = new AnnotationConfigApplicationContext(Client.class);
    }

    /**
     * 1. 简单测测试aop的使用
     * 2. 比较获取的代理的和没代理的bean的区别。 发现被代理的bean是CGLIB动态代理的bean,没被代理的就是简单的bean
     */
    @Test
    public void test01() {
        PeopleService bean = ac.getBean(PeopleService.class);
        DogManager bean1 = ac.getBean(DogManager.class);
        bean.eat("大便");
        bean.run("厕所");
    }
}
