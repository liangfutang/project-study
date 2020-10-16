package com.zjut.study.spring.cycledependency.simplespringcycle;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 测试简单的spring循环依赖
 */
@ComponentScan()
public class Client extends CommonJunitFilter {

    /**
     * 目的：测试spring的循环依赖，
     * 总结：spring生命周期中实例化完，才赢的是注入方式，所以可以不产生循环依赖的异常，默认单例情况下是支持循环依赖的
     *
     * 测试结果如下：
     *      新建Cat
     *      11:08:26.025 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'dog'
     *      新建Dog
     *      测试中的Cat
     *      测试中的dog
     */
    @Test
    public void test01() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Client.class);
        Dog dog = ac.getBean(Dog.class);
        Cat cat = ac.getBean(Cat.class);

        dog.testCatAutowired();
        cat.testDogAutowired();
    }

    /**
     * 目的：测试手动关闭spring的循环依赖
     * 总结：手动关闭spring循环依赖异常
     *
     * 测试结果如下：
     *      新建Cat
     *      11:21:16.387 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'dog'
     *      新建Dog
     *      java.lang.IllegalStateException: GenericApplicationContext does not support multiple refresh attempts: just call 'refresh' once
     * 	        at org.springframework.context.support.GenericApplicationContext.refreshBeanFactory(GenericApplicationContext.java:266)
     * 	        at org.springframework.context.support.AbstractApplicationContext.obtainFreshBeanFactory(AbstractApplicationContext.java:637)
     * 	        at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:522)
     * 	        at com.zjut.study.spring.cycledependency.simplespringcycle.Client.test02(Client.java:48)
     */
    @Test
    public void test02() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Client.class);
        AbstractAutowireCapableBeanFactory beanFactory = (AbstractAutowireCapableBeanFactory) ac.getBeanFactory();
        // 手动关闭循环依赖
        beanFactory.setAllowCircularReferences(false);
        ac.register(Client.class);
        ac.refresh();
    }


}
