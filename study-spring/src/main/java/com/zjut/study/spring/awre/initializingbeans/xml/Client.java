package com.zjut.study.spring.awre.initializingbeans.xml;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * 测试xml版本的生命周期回调的客户端
 */
@ComponentScan("com.zjut.study.spring.awre.initializingbeans.xml")
@ImportResource("classpath:awre/spring.xml")
public class Client extends CommonJunitFilter {

    /**
     * 1. 当AnnotationConfigApplicationContext中不传参数时，只是扫描了，没有实例化，此时不会打印出构造函数中的日志
     */
    @Test
    public void test1() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Client.class);
        // 此步不调的话不会触发@PreDestroy的销毁回调
        ac.close();
    }

}
