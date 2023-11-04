package com.zjut.study.es.boot.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 1，@Document(indexName ="products"，createIndex = true) 用在类上 作用:代表个对象为一个文档
 * -- indexName属性: 创建索引的名称
 * -- createIndex属性: 是否创建索引
 *
 * 2. @Id 用在属性上 作用:将对象id字段与ES中文档的_id对应
 *
 * 3.@Field(type = FieldType.Keyword) 用在属性上 作用:用来描述属性在ES中存储类型以及分词情况
 * -- type:用来指定字段类型
 */
@Document(indexName = "products", createIndex = true)
@Data
public class Product {
    @Id // 用来将放入对象的id值作为文档的_id进行映射
    private Integer id;
    @Field(type = FieldType.Keyword)
    private String title;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Date, format = DateFormat.date_time_no_millis)
    private Date createdAt;
    @Field(type = FieldType.Text)
    private String description;
}
