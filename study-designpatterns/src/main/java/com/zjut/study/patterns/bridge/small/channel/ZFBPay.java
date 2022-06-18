package com.zjut.study.patterns.bridge.small.channel;

import com.zjut.study.patterns.bridge.small.paymodel.PayModel;
import lombok.extern.slf4j.Slf4j;

/**
 * 支付宝支付
 * @author jack
 */
@Slf4j
public class ZFBPay extends Pay{

    public ZFBPay(PayModel payModel) {
        super(payModel);
    }

    @Override
    public String transfer(String uid, String tradeId) {
        log.info("开始支付宝安全校验");
        if (this.payModel.security(uid)) {
            log.error("支付宝支付环境不安全");
            return "fail transfer";
        }
        log.info("支付宝环境安全校验");
        return "success";
    }
}
