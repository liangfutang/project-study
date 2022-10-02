package com.zjut.study.spring.cycledependency.simplejavacycle;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.spring.cycledependency.simplejavacycle.initcycle.Cat;
import com.zjut.study.spring.cycledependency.simplejavacycle.initcycle.Dog;
import com.zjut.study.spring.cycledependency.simplejavacycle.objectcycle.A;
import org.junit.Test;

/**
 * 简单的java对象间的循环依赖
 * @author jack
 */
public class Client extends CommonJunitFilter {

    /**
     * 测试简单的对象间的循环依赖，对象创建完成后再set进去
     */
    @Test
    public void test01() {
        Dog dog = new Dog();
        Cat cat = new Cat();
        dog.setCat(cat);
        cat.setDog(dog);

        dog.getCatName();
        cat.getDogName();
    }

    /**
     * 对象创建过程中初始化属性，属性对象内部又需要创建属性，都是在对象的创建过程中进行
     * 线程中循环依赖的栈深度大于虚拟机所允许的深度了爆出异常StackOverflowError，
     * 测试结果如下：
     *      java.lang.StackOverflowError
     *      	at com.zjut.study.spring.cycledependency.simplejavacycle.objectcycle.B.<init>(B.java:4)
     *      	at com.zjut.study.spring.cycledependency.simplejavacycle.objectcycle.A.<init>(A.java:4)
     * 	        at com.zjut.study.spring.cycledependency.simplejavacycle.objectcycle.B.<init>(B.java:4)
     *      	at com.zjut.study.spring.cycledependency.simplejavacycle.objectcycle.A.<init>(A.java:4)
     *          ......
     */
    @Test
    public void test02() {
        // 实例化A或者B时会爆出栈溢出error
        A a = new A();
//        B b = new B();
    }
}
