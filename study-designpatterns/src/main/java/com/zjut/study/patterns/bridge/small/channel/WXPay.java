package com.zjut.study.patterns.bridge.small.channel;

import com.zjut.study.patterns.bridge.small.paymodel.PayModel;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信支付
 */
@Slf4j
public class WXPay extends Pay{
    public WXPay(PayModel payModel) {
        super(payModel);
    }

    @Override
    public String transfer(String uid, String tradeId) {
        log.info("开始微信支付校验环境是否安全");
        boolean security = this.payModel.security(uid);
        if (!security) {
            log.error("微信支付校验环境不安全");
            return "fail transfer";
        }
        log.info("支付环境安全");
        return "success";
    }
}
