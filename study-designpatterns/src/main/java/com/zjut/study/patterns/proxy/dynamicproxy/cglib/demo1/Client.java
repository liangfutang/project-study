package com.zjut.study.patterns.proxy.dynamicproxy.cglib.demo1;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;

public class Client extends CommonJunitFilter {

    /**
     * 方法级别代理
     */
    @Test
    public void test() {
        HelloService proxy = (HelloService) CustomMethodInterceptor.createProxy(HelloService.class);
        proxy.say();
    }

    /**
     * 类级别代理
     */
    @Test
    public void test01() {
        HelloService proxy = (HelloService) CustomInvocationHandler.createProxy(new HelloService());
        proxy.say();
    }
}
