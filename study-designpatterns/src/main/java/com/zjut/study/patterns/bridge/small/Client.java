package com.zjut.study.patterns.bridge.small;

import com.zjut.study.patterns.bridge.small.channel.Pay;
import com.zjut.study.patterns.bridge.small.channel.WXPay;
import com.zjut.study.patterns.bridge.small.channel.ZFBPay;
import com.zjut.study.patterns.bridge.small.paymodel.FacePayModel;
import com.zjut.study.patterns.bridge.small.paymodel.FinngerPayModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author jack
 */
@Slf4j
public class Client {

    @Test
    public void test01() {
        log.info("微信扫脸支付");
        Pay wxFacePay = new WXPay(new FacePayModel());
        wxFacePay.transfer("121324", "aaa");
        log.info("微信扫脸支付结束");

        log.info("微信指纹支付");
        Pay wxFinngerPay = new WXPay(new FinngerPayModel());
        wxFinngerPay.transfer("121325", "aaa");
        log.info("微信指纹支付结束");

        log.info("支付宝扫脸支付");
        Pay zfbFacePay = new ZFBPay(new FacePayModel());
        zfbFacePay.transfer("121324", "aaa");
        log.info("支付宝扫脸支付结束");

        log.info("支付宝指纹支付");
        Pay zfbFinngerPay = new ZFBPay(new FinngerPayModel());
        zfbFinngerPay.transfer("121325", "aaa");
        log.info("支付宝指纹支付结束");
    }
}
