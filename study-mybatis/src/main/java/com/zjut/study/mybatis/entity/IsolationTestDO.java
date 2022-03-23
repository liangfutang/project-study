package com.zjut.study.mybatis.entity;

/**
 * 对数据库isolation_test表结果的映射
 * @author jack
 */
public class IsolationTestDO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 是否审核，0:否，1:是
     */
    private Integer audit;
}
