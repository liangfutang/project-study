package com.zjut.study.others.java.reflect.differences;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;

public class Client extends CommonJunitFilter {

    private final String className = "com.zjut.study.others.java.reflect.differences.Person";


    /**
     * 目的：测试Class.forName，内部默认会初始化
     * 总结：测试结果显示会加载出所有的静态属性和静态代码块
     *
     * 测试结果如下：
     *      小明的小宠物狗
     *      小明的静态方法块
     *
     * @throws ClassNotFoundException
     */
    @Test
    public void test01() throws ClassNotFoundException {
        Class<?> aClass = Class.forName(className);
    }

    /**
     * 目的：通过Class.forName内部的方法发射，修改Class.forName默认的初始化为false，不做初始化操作
     * 总结：不会初始化操作
     *
     * 测试结果如下：
     *
     * @throws ClassNotFoundException
     */
    @Test
    public void test02() throws ClassNotFoundException {
        Class<?> aClass = Class.forName(className, false, ClassLoader.getSystemClassLoader());
    }

    /**
     * 目的：通过ClassLoader内部的方法反射
     * 总结：不会初始化操作
     *
     * 测试结果如下：
     *
     * @throws ClassNotFoundException
     */
    @Test
    public void test03() throws ClassNotFoundException {
        ClassLoader.getSystemClassLoader().loadClass(className);
    }
}
