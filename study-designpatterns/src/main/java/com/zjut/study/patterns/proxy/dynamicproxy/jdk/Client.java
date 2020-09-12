package com.zjut.study.patterns.proxy.dynamicproxy.jdk;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        TargetClass o = (TargetClass) Proxy.newProxyInstance(TargetClass.class.getClassLoader(), new Class[]{TargetClass.class}, new ProxyClass(new TargetClassImpl()));
        o.sayHi();
        System.out.println("\n\n\n");

        // 测试InvocationHandler中的invoke
        o.testInvocationHandlerInvokeArgs0("jack", 21).testInvocationHandlerInvokeArgs0("rose", 20).testInvocationHandlerInvokeArgs0("hanmeimei", 23);

    }
}
