package com.zjut.study.spring.awre.interfaces;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 测试接口实现版本的回调
 */
@Component
public class Foo  implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("接口版本 init method invoked");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("接口版本 destory method invoked");
    }
}
