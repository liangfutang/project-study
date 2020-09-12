package com.zjut.study.patterns.proxy.staticproxy;

/**
 * 目标实现
 */
public class TargetClassImpl implements TargetClass {
    @Override
    public void sayHi() {
        System.out.println("hi");
    }
}
