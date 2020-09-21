package com.zjut.study.spring.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class FactoryBeanService  implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        TargetClass targetClass = new TargetClass();
        targetClass.setName("jack");
        return targetClass;
    }

    @Override
    public Class<?> getObjectType() {
        return TargetClass.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
