package com.zjut.study.spring.container.addandremove;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.spring.container.addandremove.entity.NoRegisterBefore;
import com.zjut.study.spring.container.addandremove.entity.RelyBean;
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
     * 容器中动态删除一个bean(被依赖的)
     * 被依赖的会被删除，依赖的还存在
     * 如果在springboot中，本次请求能正常的请求完成，下次再请求进来的时候会报异常
     */
    @Test
    public void remove() {
        String willRemoveBean = RemoveBeanWill.getBeanName();

        System.out.println("容器中存在: " + ac.containsBean(willRemoveBean));

        BeanDefinitionRegistry beanDefReg = (DefaultListableBeanFactory) ac.getBeanFactory();
        beanDefReg.removeBeanDefinition(willRemoveBean);

        System.out.println("容器中存在: " + ac.containsBean(willRemoveBean));
        System.out.println("容器中依赖对象还存在么: " + ac.containsBean(RelyBean.getBeanName()));
    }

    /**
     * 删除依赖的bean
     * 依赖的 对象删除，被依赖的对象还存在
     * 如果在springboot中，本次请求能正常的请求完成，下次再请求进来的时候会报异常
     */
    @Test
    public void removeRely() {
        String beanName = RelyBean.getBeanName();

        System.out.println("依赖的对象是否存在: " + ac.containsBean(beanName));
        BeanDefinitionRegistry beanDefReg = (DefaultListableBeanFactory) ac.getBeanFactory();
        beanDefReg.removeBeanDefinition(beanName);

        System.out.println("依赖的对象是否存在: " + ac.containsBean(beanName));
        // 依赖的对象被删除，被注入的对象还存在
        System.out.println("被依赖的对象是否存在: " + ac.containsBean(RemoveBeanWill.getBeanName()));
        // 通过下面直接获取bean的操作会导致重新注入当前bean到容器中，会应为依赖的bean不存在而导致异常发生
        try {
            RelyBean relyBean = ac.getBean(RelyBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
