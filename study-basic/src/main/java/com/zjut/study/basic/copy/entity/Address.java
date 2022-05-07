package com.zjut.study.basic.copy.entity;

import lombok.Getter;

@Getter
public class Address implements Cloneable {
    private String name;

    public Address(String name) {
        this.name = name;
    }

    public Address() {}

    @Override
    public Address clone() throws CloneNotSupportedException {
        return (Address) super.clone();
    }
}
