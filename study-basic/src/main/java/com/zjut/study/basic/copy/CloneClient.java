package com.zjut.study.basic.copy;

import com.zjut.study.basic.copy.entity.Address;
import com.zjut.study.basic.copy.entity.Person;
import com.zjut.study.basic.copy.entity.School;
import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;

/**
 * 使用clone:
 * ① 实现Cloneable接口，这是一个标记接口，自身没有方法。(不实现调用会出现异常)
 * ② 覆盖clone()方法，可见性提升为public。
 */
public class CloneClient extends CommonJunitFilter {

    @Test
    public void test01() throws CloneNotSupportedException {
        Address ad = new Address("安徽");
        School nbSchool = new School("牛逼小学");
        Person p1 = new Person("jack", 12, ad, nbSchool);

        Person p2 = p1.clone();
        System.out.println(p1);
        System.out.println(p2);
        // false: 两个对象
        System.out.println(p1 == p2);
        // false: 拷贝过，不是一个对象
        System.out.println(p1.getAddress() == p2.getAddress());
        // true: clone浅拷贝，School对象没做特殊处理
        System.out.println(p1.getSchool() == p2.getSchool());
    }
}
