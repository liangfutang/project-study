package com.zjut.study.patterns.proxy.dynamicproxy.cglib;


/**
 * 目标实现
 */
public class TargetClassImpl {

    public void sayHi() {
        System.out.println("hi, cglib dynamic proxy");
    }

    public void sayHiMethodProxyInvoke() {
        System.out.println("hi, cglib dynamic proxy");
    }

    public void sayHiMethodProxyinvokeSuper() {
        System.out.println("hi, cglib dynamic proxy");
    }

    /**
     * cglig动态代理，代理类继承目标类，所以该方法应该是不被代理的
     */
    final public void testFinal() {
        System.out.println("hi, cglib dynamic proxy, test final");
    }

    public TargetClassImpl testInvocationHandlerInvokeArgs0(String name, Integer age) {
        System.out.println("cglib dynamic proxy接受到的name:" + name + ", age:" + age);
        return null;
    }
}
