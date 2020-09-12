package com.zjut.study.patterns.proxy.staticproxy;

public class ProxyClass implements TargetClass {
    private TargetClass targetClass;
    public ProxyClass(TargetClass targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public void sayHi() {
        System.out.println("前置增强");
        targetClass.sayHi();
        System.out.println("后置增强");
    }
}
