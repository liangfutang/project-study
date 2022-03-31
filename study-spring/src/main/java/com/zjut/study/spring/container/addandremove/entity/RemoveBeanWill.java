package com.zjut.study.spring.container.addandremove.entity;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 注入到容器中的bean，容器启动成功后会移除测试
 * @author jack
 */
@Component
public class RemoveBeanWill implements InitializingBean, BeanNameAware {
    /**
     * 当前Bean的名称
     */
    private static String BEAN_NAME;

    @Resource
    private RelyBean relyBean;

    public static String getBeanName() {
        return BEAN_NAME;
    }








    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(BEAN_NAME+ " 依赖了  " + relyBean);
    }

    @Override
    public void setBeanName(String beanName) {
        BEAN_NAME = beanName;
    }
}
