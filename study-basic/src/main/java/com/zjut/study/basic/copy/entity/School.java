package com.zjut.study.basic.copy.entity;

import lombok.Data;
import lombok.Getter;

@Getter
public class School {

    private String name;

    public School(String name) {
        this.name = name;
    }

}
