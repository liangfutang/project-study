package com.zjut.study.patterns.template.demo;

import lombok.Data;

/**
 * 选定的卡信息
 */
@Data
public class SimcardInfo {
    /**
     * 手机号
     */
    private String msisdn;

    /**
     * 基础流量套餐
     * 单位：M
     */
    private Integer baseData;

    /**
     * 短信数量
     * 单位：条
     */
    private Integer smsCount;

    /**
     * 该手机卡是几G的
     */
    private String g;

    /**
     * 手机卡基础套餐名
     */
    private String name;
}
