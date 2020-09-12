package com.zjut.study.patterns.proxy.staticproxy;

public class Client {
    public static void main(String[] args) {
        TargetClass targetClass = new ProxyClass(new TargetClassImpl());
        targetClass.sayHi();
    }
}
