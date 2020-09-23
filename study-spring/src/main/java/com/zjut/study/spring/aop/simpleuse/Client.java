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
     * 简单测测试aop的使用
     */
    @Test
    public void test01() {
        PeopleService bean = ac.getBean(PeopleService.class);
        bean.eat("大便");
        bean.run("厕所");
    }
}
