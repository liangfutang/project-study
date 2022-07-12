package com.zjut.study.boot.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 实现ApplicationContextAware ，获取ApplicationContext上下文
 * 实现BeanNameAware，获取当前bean的name
 * @author jack
 */
@Component
public class ApplicationContextBeanName implements ApplicationContextAware, BeanNameAware {

    private ApplicationContext applicationContext;

    /**
     * 获取上下文
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取到当前bean的name
     * @param beanName
     */
    @Override
    public void setBeanName(@NonNull String beanName) {
        System.out.println("当前bean的名称是:" + beanName);
    }
}
