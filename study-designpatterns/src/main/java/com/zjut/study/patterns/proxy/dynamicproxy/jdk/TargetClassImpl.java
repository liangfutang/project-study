package com.zjut.study.patterns.proxy.dynamicproxy.jdk;

/**
 * 目标实现
 */
public class TargetClassImpl implements TargetClass {
    @Override
    public void sayHi() {
        System.out.println("hi, jdk dynamic proxy");
    }

    @Override
    public TargetClass testInvocationHandlerInvokeArgs0(String name, Integer age) {
        System.out.println("jdk dynamic proxy接受到的name:" + name + ", age:" + age);
        return null;
    }
}
