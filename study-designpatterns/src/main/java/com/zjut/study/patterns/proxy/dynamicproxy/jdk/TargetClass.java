package com.zjut.study.patterns.proxy.dynamicproxy.jdk;

/**
 * 目标接口
 */
public interface TargetClass {
    /**
     * 测试代理方法
     */
    void sayHi();

    /**
     * 测试InvocationHandler相关参数
     */
    TargetClass testInvocationHandlerInvokeArgs0(String name, Integer age);
}
