package com.zjut.study.patterns.proxy.dynamicproxy.cglib.demo1;

public class HelloService {

    public void say() {
        System.out.println("\n你好\n");
        this.hi();
    }

    public void hi() {
        System.out.println("\nhi\n");
    }
}
