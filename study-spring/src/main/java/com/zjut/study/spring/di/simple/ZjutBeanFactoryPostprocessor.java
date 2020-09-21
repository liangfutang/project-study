package com.zjut.study.spring.di.simple;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * 提供一个后置处理器来获取B的自动装配模型
 */
@Component
public class ZjutBeanFactoryPostprocessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        GenericBeanDefinition b = (GenericBeanDefinition) beanFactory.getBeanDefinition("b");
        System.out.println("显示B的注入模型:" + b.getAutowireMode());
    }
}
