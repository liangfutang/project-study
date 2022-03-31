package com.zjut.study.spring.container.addandremove.entity;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

/**
 * 容器中依赖bean
 * @author jack
 */
@Component
public class RelyBean implements BeanNameAware {
    /**
     * 当前Bean的名称
     */
    private static String BEAN_NAME;

    public static String getBeanName() {
        return BEAN_NAME;
    }

    /**
     * 用来测试当前对象是否还存在
     * @return 正常返回则对象还存在
     */
    public String testHealth () {
        return "ok";
    }



    @Override
    public void setBeanName(String beanName) {
        BEAN_NAME = beanName;
    }
}
