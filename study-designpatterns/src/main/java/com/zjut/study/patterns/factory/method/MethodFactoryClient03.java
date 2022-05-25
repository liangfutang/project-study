package com.zjut.study.patterns.factory.method;

import java.lang.reflect.Constructor;

/**
 * @see ./pic/使用工厂方法模式替代单例模式类图.png
 * @author jack
 */
public class MethodFactoryClient03 {
    public static void main(String[] args) {
        Singleton singleton = SingletonFactory.getSingleton();
        singleton.doSomething();
    }
}

//=============================================

class SingletonFactory {
    private static Singleton singleton;

    static {
        try {
            Class<?> aClass = Class.forName(Singleton.class.getName());
            Constructor<?> constructor = aClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            singleton = (Singleton) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Singleton getSingleton(){
        return singleton;
    }

}

//======================================================

class Singleton {
    private Singleton() {}

    public void doSomething() {}
}
