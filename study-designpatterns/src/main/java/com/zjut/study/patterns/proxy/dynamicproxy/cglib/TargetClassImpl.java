package com.zjut.study.patterns.proxy.dynamicproxy.cglib;


/**
 * 目标实现
 */
public class TargetClassImpl {

    public void sayHi() {
        System.out.println("hi, cglib dynamic proxy");
    }

    public TargetClassImpl testInvocationHandlerInvokeArgs0(String name, Integer age) {
        System.out.println("cglib dynamic proxy接受到的name:" + name + ", age:" + age);
        return null;
    }
}
