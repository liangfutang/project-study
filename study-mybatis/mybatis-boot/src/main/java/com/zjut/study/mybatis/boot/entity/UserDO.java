package com.zjut.study.mybatis.boot.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class UserDO {
    /**
     * '主键'
     */
    private Integer id;
    /**
     * '姓名'
     */
    private String name;
    /**
     * '年龄'
     */
    private Integer age;
    /**
     * '性别，1:男，2:女'
     */
    private Integer sex;
    /**
     * '创建时间'
     */
    private Date createTime;
    /**
     * '更新时间'
     */
    private Date updateTime;
}
