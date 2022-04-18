package com.zjut.study.patterns.proxy.dynamicproxy.jdk.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * ProxyClass是动态代理的一部分，不是真正的代理类，是协助真正的代理类去工作的
 */
public class ProxyClass implements InvocationHandler {
    // 代理的接口实现类，对该类实现代理，在该类方法前后实现逻辑
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
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk代理前显示一下接受到的参数:" + ((args==null||args.length<=0) ? args : Arrays.asList(args)));

        System.out.println("jdk代理方法执行前");
        Object invoke = method.invoke(target, args);
        System.out.println("jdk代理方法执行后");

        if (TEST_METHOD.equals(method.getName())) {
            System.out.println("jdk返回代理对象\n");
            return proxy;
        }
        return invoke;
    }
}
