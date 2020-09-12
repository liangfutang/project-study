package com.zjut.study.patterns.proxy.dynamicproxy.cglib;

import net.sf.cglib.proxy.Enhancer;


public class Client {
    public static void main(String[] args) {
        // 1. 创建方式一
        Enhancer enhancer = new Enhancer();
        //设置目标类的字节码文件
        enhancer.setSuperclass(TargetClassImpl.class);
        //设置回调函数
        enhancer.setCallback(new ProxyClass(new TargetClassImpl()));
        //这里的creat方法就是正式创建代理类
        TargetClassImpl o = (TargetClassImpl)enhancer.create();
        o.sayHi();
        System.out.println("\n\n\n");

        // 2. 创建方式二
//        TargetClassImpl o = (TargetClassImpl) Enhancer.create(TargetClassImpl.class, new ProxyClass(new TargetClassImpl()));

        // 测试InvocationHandler中的invoke
        o.testInvocationHandlerInvokeArgs0("jack", 21).testInvocationHandlerInvokeArgs0("rose", 20).testInvocationHandlerInvokeArgs0("hanmeimei", 23);

    }
}
