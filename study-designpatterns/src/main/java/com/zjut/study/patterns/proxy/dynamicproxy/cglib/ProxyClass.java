package com.zjut.study.patterns.proxy.dynamicproxy.cglib;


import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyClass implements MethodInterceptor {
    private Object target;
    public ProxyClass(Object target) {
        this.target = target;
    }

    private final String TEST_METHOD = "testInvocationHandlerInvokeArgs0";

    /**
     *
     * @param proxy 代理对象，不是this对象，可以将代理对象返回以进行连续调用。可以使用反射获取代理对象的信息（也就是proxy.getClass().getName()）。
     * @param method 被代理方法
     * @param args 被代理方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib代理前显示一下接受到的参数:" + ((args==null||args.length<=0) ? args : Arrays.asList(args)));
        System.out.println("cglib代理方法:" + methodProxy);

        System.out.println("cglib代理方法执行前");
        Object invoke = method.invoke(target, args);
        System.out.println("cglib代理方法执行后");

        if (TEST_METHOD.equals(method.getName())) {
            System.out.println("cglib返回代理对象\n");
            return proxy;
        }
        return invoke;
    }

}
