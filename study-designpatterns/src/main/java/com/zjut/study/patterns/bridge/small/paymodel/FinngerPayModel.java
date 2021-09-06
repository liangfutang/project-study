package com.zjut.study.patterns.bridge.small.paymodel;

import lombok.extern.slf4j.Slf4j;

/**
 * 指纹支付
 */
@Slf4j
public class FinngerPayModel implements PayModel{
    @Override
    public boolean security(String uid) {
        log.info("指纹支付");
        return true;
    }
}
