package com.zjut.study.spring.awre.beanaware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 测试注解版本的回调
 * 经测试调用的先后顺序为：BeanNameAware -> BeanFactoryAware -> ApplicationContextAware -> @PostConstruct(InitializingBean) -> @PreDestroy(DisposableBean)
 */
@Component
public class Food implements BeanFactoryAware, BeanNameAware, ApplicationContextAware, InitializingBean, DisposableBean {

    @Autowired
    private Bread bread;

    public Food() {
        System.out.println("构建Food....");
    }

    public void setBread(Bread bread) {
        this.bread = bread;
        System.out.println("通过setter方式来注入bread");
    }

    public void eat(String name) {
        System.out.println("小狗吃" + name);
    }

    /**
     * 该注解表示一个方法:javax.annotation.PostConstruct,spring容器,在bean创建之后,在初始化之前调用!!
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception{
        System.out.println("beanawre init method is called");
    }

    /**
     * 该注解表示一个方法:javax.annotation.PreDestory,在初始化之后,在销毁之前!!
     * @throws Exception
     */
    @PreDestroy
    public void destory() throws RuntimeException{
        System.out.println("beanawre destory method is called");
    }

    //===============================华丽的分割线==================================
    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;
    /**
     * 调用BeanFactoryAware的setBeanFactory()
     * @param beanFactory
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println("调用BeanFactoryAware的setBeanFactory()...传入:" + beanFactory);
    }

    /**
     * 调用BeanNameAware的setBeanName()
     * @param s
     */
    @Override
    public void setBeanName(String s) {
        System.out.println("调用BeanNameAware的setBeanName()...传入:" + s );
    }

    /**
     * 调用ApplicationContextAware的setApplicationContext()
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("调用ApplicationContextAware的setApplicationContext()...传入:" + applicationContext);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("销毁bean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化bean");
    }
}
