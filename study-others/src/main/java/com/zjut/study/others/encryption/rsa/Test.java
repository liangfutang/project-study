package com.zjut.study.others.encryption.rsa;

public class Test extends Person implements Anymal {
    @Override
    public void sya(String song) {

    }
}

abstract class Person implements Anymal{
    @Override
    public void eat(String name) {

    }
}

interface Anymal {
    void eat(String name);
    void sya(String song);
}
