package com.zjut.study.patterns.proxy.dynamicproxy.jdk;

/**
 * 目标实现
 */
public class TargetClassImpl implements TargetClass {
    @Override
    public void sayHi() {
        System.out.println("hi, dynamic proxy");
    }

    @Override
    public TargetClass testInvocationHandlerInvokeArgs0(String name, Integer age) {
        System.out.println("接受到的name:" + name + ", age:" + age);
        return null;
    }
}
