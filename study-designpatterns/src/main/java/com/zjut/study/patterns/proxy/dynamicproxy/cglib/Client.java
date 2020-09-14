package com.zjut.study.patterns.proxy.dynamicproxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class Client {

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

    // ====================================================================华丽的分割线===============================================================================

    @Before
    public void beforeAll() {
        System.out.println("开始...\n");
        // 代理类class文件存入本地磁盘方便我们反编译查看源码
//      System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\code");
    }

    @After
    public void afterAll() {
        System.out.println("\n结束...");
    }
}
