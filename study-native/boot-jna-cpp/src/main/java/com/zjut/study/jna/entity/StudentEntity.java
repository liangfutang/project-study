package com.zjut.study.jna.entity;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class StudentEntity extends Structure {
    public static class ByReference extends StudentEntity implements Structure.ByReference { }
    public static class ByValue extends StudentEntity implements Structure.ByValue { }

    public String name;
    public int age;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("name", "age");
    }
}
