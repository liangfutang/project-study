package com.zjut.study.reactive.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Book {
    /**
     * 图书编号
     */
    private String isbn;
    /**
     * 主题
     */
    private String title;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 类别
     */
    private String category;
}
