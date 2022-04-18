package com.zjut.study.patterns.proxy.dynamicproxy.jdk;

import com.zjut.study.patterns.proxy.dynamicproxy.jdk.proxy.ProxyClass;
import com.zjut.study.patterns.proxy.dynamicproxy.jdk.proxy.TargetClass;
import com.zjut.study.patterns.proxy.dynamicproxy.jdk.proxy.TargetClassImpl;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * jdk动态代理
 */
public class JDKProxyClient {

    @Test
    public void test01() {
        TargetClass o = (TargetClass) Proxy.newProxyInstance(
                TargetClass.class.getClassLoader(),
                new Class[]{TargetClass.class},
                new ProxyClass(new TargetClassImpl()));
        o.sayHi();
        System.out.println("\n\n\n");

        // 测试InvocationHandler中的invoke
        o.testInvocationHandlerInvokeArgs0("jack", 21).testInvocationHandlerInvokeArgs0("rose", 20).testInvocationHandlerInvokeArgs0("hanmeimei", 23);

    }
}
