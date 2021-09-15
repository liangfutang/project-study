package com.zjut.study.patterns.proxy.dynamicproxy.cglib.demo1;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 基于方法级别生成代理类,该级别的代理生成的代理类能内部直接调用，代理不会失效
 */
public class CustomMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
        System.out.println("代理拦截前，当前使用方法:" + method.getName());
        Object invoke = proxy.invokeSuper(o, objects);
        System.out.println("代理拦截后,使用的方法:" + method.getName());
        return invoke;
    }

    /**
     * 创建出对应的代理对象
     * @param clazz
     * @return
     */
    public static Object createProxy(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new CustomMethodInterceptor());
        return enhancer.create();
    }
}
