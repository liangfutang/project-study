package com.zjut.study.spring.container.addandremove;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.spring.container.addandremove.entity.NoRegisterBefore;
import com.zjut.study.spring.container.addandremove.entity.RemoveBeanWill;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.zjut.study.spring.container.addandremove")
public class Client  extends CommonJunitFilter {

    private AnnotationConfigApplicationContext ac;

    @Before
    public void before() {
        ac = new AnnotationConfigApplicationContext(Client.class);
    }

    /**
     * spring容器启动后向容器中动态添加bean
     */
    @Test
    public void add() {
        boolean exist1 = ac.containsBean(NoRegisterBefore.BEAN_NAME);
        System.out.println("池子中包含吗: " + exist1);

        BeanDefinitionRegistry beanDefReg = (DefaultListableBeanFactory) ac.getBeanFactory();
        if (!beanDefReg.containsBeanDefinition(NoRegisterBefore.BEAN_NAME)) {
            BeanDefinitionBuilder beanDefBuilder = BeanDefinitionBuilder.genericBeanDefinition(NoRegisterBefore.class);
            BeanDefinition beanDef = beanDefBuilder.getBeanDefinition();
            ac.registerBeanDefinition(NoRegisterBefore.BEAN_NAME, beanDef);
        }

        boolean exist2 = ac.containsBean(NoRegisterBefore.BEAN_NAME);
        System.out.println("池子中包含吗: " + exist2);
    }

    /**
     * 容器中动态删除一个bean
     */
    @Test
    public void remove() {
        String willRemoveBean = RemoveBeanWill.getBeanName();

        System.out.println("容器中存在: " + ac.containsBean(willRemoveBean));

        BeanDefinitionRegistry beanDefReg = (DefaultListableBeanFactory) ac.getBeanFactory();
        beanDefReg.removeBeanDefinition(willRemoveBean);

        System.out.println("容器中存在: " + ac.containsBean(willRemoveBean));
    }
}
