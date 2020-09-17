package com.zjut.study.spring.awre.xml;

/**
 * 测试接口实现版本的回调
 */
public class Foo {

    public void init() throws Exception{
        System.out.println("annotate 版本的初始化 init method is called");
    }

    public void destory() throws RuntimeException{
        System.out.println("annotate 版本的销毁 destory method is called");
    }
}
