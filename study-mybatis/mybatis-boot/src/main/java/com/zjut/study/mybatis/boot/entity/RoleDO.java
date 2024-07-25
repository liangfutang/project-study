package com.zjut.study.mybatis.boot.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class RoleDO {
    /**
     * '主键'
     */
    private Integer id;
    /**
     * '角色名'
     */
    private String name;
    /**
     * '描述'
     */
    private String desc;
    /**
     * '创建时间'
     */
    private Date createTime;
    /**
     * '更新时间'
     */
    private Date updateTime;
}
