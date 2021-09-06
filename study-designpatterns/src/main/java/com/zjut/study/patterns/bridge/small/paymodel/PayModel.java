package com.zjut.study.patterns.bridge.small.paymodel;

/**
 * 支付方式
 */
public interface PayModel {

    /**
     * 支付环境是否安全
     * @param uid
     * @return
     */
    boolean security(String uid);
}
