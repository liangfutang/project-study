package com.zjut.study.patterns.bridge.small.paymodel;

import lombok.extern.slf4j.Slf4j;

/**
 * 脸部扫描支付
 */
@Slf4j
public class FacePayModel implements PayModel{
    @Override
    public boolean security(String uid) {
        log.info("脸部支付");
        return true;
    }
}
