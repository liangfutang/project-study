package com.zjut.study.basic.differences;

import lombok.Getter;

public class Person {

    private static Dog dog = new Dog();

    static {
        System.out.println("小明的静态方法块");
    }

    public static void test() {
        System.out.println("小明的静态方法中");
    }

    @Getter
    private String name;

    {
        name = "小明";
        System.out.println("小明的普通方法快");
    }

    private static class Dog {
        public Dog() {
            System.out.println("小明的小宠物狗");
        }
    }

    public static void main(String[] args) {
        Person person = new Person();
        System.out.println(person.getName());
    }
}
