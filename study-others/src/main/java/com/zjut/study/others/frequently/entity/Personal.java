package com.zjut.study.others.frequently.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Personal {
    private Integer age;
    private String name;
}
