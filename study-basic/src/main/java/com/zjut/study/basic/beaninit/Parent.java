package com.zjut.study.basic.beaninit;

public class Parent {

    private static OneStatic oneStatic = new OneStatic();
    private One one = new One();

    static {
        System.out.println("父类的静态代码块");
    }

    {
        System.out.println("父类的代码块");
    }

    public Parent() {
        System.out.println("父类的构造函数");
    }

    private static class OneStatic{
        public OneStatic() {
            System.out.println("父类的静态属性");
        }
    }

    private class One{
        public One() {
            System.out.println("父类的属性");
        }
    }
}
