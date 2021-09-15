package com.zjut.study.patterns.proxy.dynamicproxy.cglib.demo;

import com.zjut.study.common.junit.CommonJunitFilter;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

/**
 * 测试cglib动态代理的客户端
 */
public class Client extends CommonJunitFilter {

    /**
     * 通过新建Enhancer的方式创建代理类
     */
    @Test
    public void testCreatProxy1() {
        Enhancer enhancer = new Enhancer();
        //设置目标类的字节码文件
        enhancer.setSuperclass(TargetClassImpl.class);
        //设置回调函数
        enhancer.setCallback(new ProxyClass(new TargetClassImpl()));
        //这里的creat方法就是正式创建代理类
        TargetClassImpl o = (TargetClassImpl)enhancer.create();

        o.sayHi();
    }

    /**
     * 通过Enhancer的静态方式创建代理类
     */
    @Test
    public void testCreatProxy2() {
        TargetClassImpl o = (TargetClassImpl) Enhancer.create(TargetClassImpl.class, new ProxyClass(new TargetClassImpl()));
        o.sayHi();
    }

    /**
     * 通过Enhancer的静态方式创建代理类
     */
    @Test
    public void testTargetMethodFinal() {
        TargetClassImpl o = (TargetClassImpl) Enhancer.create(TargetClassImpl.class, new ProxyClass(new TargetClassImpl()));
        o.testFinal();
    }

    /**
     * 测试MethodInterceptor的intercept第一个参数：代理对象
     */
    @Test
    public void testMethodInterceptorInterceptProxy() {
        TargetClassImpl o = (TargetClassImpl) Enhancer.create(TargetClassImpl.class, new ProxyClass(new TargetClassImpl()));
        o.testInvocationHandlerInvokeArgs0("jack", 21).testInvocationHandlerInvokeArgs0("rose", 20).testInvocationHandlerInvokeArgs0("hanmeimei", 23);
    }

    /**
     * 测试MethodInterceptor的intercept第四个参数：代理方法
     * methodProxy.invoke(target, args);
     */
    @Test
    public void testMethodInterceptorInterceptMethodProxy() {
        TargetClassImpl o = (TargetClassImpl) Enhancer.create(TargetClassImpl.class, new ProxyClass(new TargetClassImpl()));
        o.sayHiMethodProxyInvoke();
    }

    /**
     * 测试MethodInterceptor的intercept第四个参数：代理方法
     * methodProxy.invokeSuper(proxy, args);
     */
    @Test
    public void testMethodInterceptorInterceptMethodProxySuper() {
        TargetClassImpl o = (TargetClassImpl) Enhancer.create(TargetClassImpl.class, new ProxyClass());
        o.sayHiMethodProxyinvokeSuper();
    }

}
