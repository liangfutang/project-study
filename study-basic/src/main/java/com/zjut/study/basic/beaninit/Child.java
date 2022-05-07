package com.zjut.study.basic.beaninit;

/**
 * 用来测试实例后初始化的加载过程，测试结果如下：
 *
 * 父类的静态属性
 * 父类的静态代码块
 * 子类中的静态属性
 * 子类中的静态代码块
 *
 * 父类的属性
 * 父类的代码块
 * 父类的构造函数
 *
 * 子类中的属性值
 * 子类中的代码块
 * 子类的构造函数
 */
public class Child extends Parent {

    private static OneStatic oneStatic = new OneStatic();

    private One one = new One();

    static {
        System.out.println("子类中的静态代码块");
    }

    {
        System.out.println("子类中的代码块");
    }

    public Child() {
        System.out.println("子类的构造函数");
    }



    private static class OneStatic {
        public OneStatic() {
            System.out.println("子类中的静态属性");
        }
    }

    private class One {
        public One() {
            System.out.println("子类中的属性值");
        }
    }

    public static void main(String[] args) {
        Child child = new Child();
    }
}
