package com.zjut.study.spring.cycledependency.simplejavacycle;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;

public class Client extends CommonJunitFilter {

    /**
     * 目的：测试java对象间的简单依赖，通过注入的方式
     * 总结：通过setter的注入方式能成功注入，不会产生循环依赖的问题，因为是实例化后有了对象，所以可以依赖进去
     *
     * 测试结果如下：
     *      依赖方叫:小喵
     *      依赖方名叫:小汪
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
     * 目的：测试简单java间的循环依赖，内部直接new出实例的方式
     * 总结：测试爆出异常StackOverflowError，线程中循环依赖的栈深度大于虚拟机所允许的深度了
     *
     * 测试结果如下：
     *      java.lang.StackOverflowError
     *      	at com.zjut.study.spring.cycledependency.simplejavacycle.B.<init>(B.java:4)
     *      	at com.zjut.study.spring.cycledependency.simplejavacycle.A.<init>(A.java:4)
     * 	        at com.zjut.study.spring.cycledependency.simplejavacycle.B.<init>(B.java:4)
     *      	at com.zjut.study.spring.cycledependency.simplejavacycle.A.<init>(A.java:4)
     * 	        at com.zjut.study.spring.cycledependency.simplejavacycle.B.<init>(B.java:4)
     *      	at com.zjut.study.spring.cycledependency.simplejavacycle.A.<init>(A.java:4)
     */
    @Test
    public void test02() {
        // 只实例化一个A或者B的话也会爆出上述的异常
        A a = new A();
//        B b = new B();
    }
}
