package com.zjut.study.spring.awre.initializingbeans.interfaces;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 测试接口实现版本的回调
 */
@Component
//@Scope("prototype")   // 多例模式或导致初始化和destroy失效
public class Foo  implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("接口版本 init method invoked");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("接口版本 destory method invoked");
    }
}
