package com.zjut.study.basic.copy.entity;

import lombok.Data;

@Data
public class Person implements Cloneable{
    private String name;
    private int age;

    private Address address;
    private School school;

    public Person(String name, int age, Address address, School school) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.school = school;
    }

    @Override
    public Person clone() throws CloneNotSupportedException {
        Person person = (Person) super.clone();
        Address addressClone = this.address.clone();
        person.setAddress(addressClone);
        return person;
    }
}
