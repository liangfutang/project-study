package com.zjut.study.others.frequently.entity;

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
