package com.zjut.study.patterns.template.demo;

import lombok.Data;

/**
 * 去手机店办理SIM卡并开通套餐模板
 */
public abstract class SimcardTemplate {

    public final void buySimcard() {
        // 1. 选号
        SimcardInfo simcardInfo = this.lookForSimcard();
        Callback callback = new Callback();
        callback.setSimcardInfo(simcardInfo);

        // 2. 和营业员购买卡并做激活
        this.buy(callback);

        // 3. 办理相关套餐(激活成功后)
        this.orderPackage(callback);
    }

    // ==================================================过程方法抽象(start)=================================================================

    /**
     * 查找咨询卡号、套餐等相关信息
     */
    protected abstract SimcardInfo lookForSimcard();

    /**
     * 买卡，并做实名等相关业务
     */
    protected abstract void buy(Callback callback);

    /**
     * 套餐订购，如手机流量、宽带等业务
     */
    protected abstract void orderPackage(Callback callback);

    // ==================================================过程方法抽象(end)=================================================================

    /**
     * 用作过程之间传递参数作用
     */
    @Data
    protected static class Callback {
        /**
         * 卡信息
         */
        private SimcardInfo simcardInfo;
        /**
         * 是否已经激活成功
         */
        private boolean active = false;
    }

}
