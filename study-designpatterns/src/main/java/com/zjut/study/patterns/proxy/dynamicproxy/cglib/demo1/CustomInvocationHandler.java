package com.zjut.study.patterns.proxy.dynamicproxy.cglib.demo1;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * 类级别的代理。
 * 代理出的类，内部直接调回导致代理失效
 */
public class CustomInvocationHandler implements InvocationHandler {

    /**
     * 被代理的对象
     */
    private Object target;

    public CustomInvocationHandler(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("代理前，当前代理的方法:" + method.getName());
        Object invoke = method.invoke(target, objects);
        System.out.println("代理后，代理的方法:" + method.getName());
        return invoke;
    }

    /**
     * 生成代理对象
     * @return
     */
    public static Object createProxy(Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CustomInvocationHandler(target));
        return enhancer.create();
    }
}
