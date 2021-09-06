package com.zjut.study.patterns.bridge.small.channel;

import com.zjut.study.patterns.bridge.small.paymodel.PayModel;

/**
 * 支付工作
 */
public abstract class Pay {

    /**
     * 支付方式
     */
    protected PayModel payModel;
    public Pay(PayModel payModel) {
        this.payModel = payModel;
    }

    /**
     * 交易动作
     * @param uid
     * @param tradeId
     * @return
     */
    public abstract String transfer(String uid, String tradeId);
}
