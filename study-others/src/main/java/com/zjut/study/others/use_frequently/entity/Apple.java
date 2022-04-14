package com.zjut.study.others.use_frequently.entity;

import lombok.Data;

@Data
public class Apple {
    private String color;
    private Integer weight;
    private String origin;

    public Apple(String color, Integer weight, String origin) {
        this.color = color;
        this.weight = weight;
        this.origin = origin;
    }
}
