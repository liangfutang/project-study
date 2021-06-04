package com.zjut.study.patterns.template.demo.handler;

import com.zjut.study.patterns.template.demo.SimcardInfo;
import com.zjut.study.patterns.template.demo.SimcardTemplate;

/**
 * 买中国移动的手机卡
 * 如发现实现类很多方法实现内容基本一样，可考虑将该方法实现放到抽象模板中去，减少代码的冗余
 */
public class CMCCSimcard extends SimcardTemplate {

    @Override
    public SimcardInfo lookForSimcard() {
        SimcardInfo simcardInfo = new SimcardInfo();
        simcardInfo.setG("5G");
        simcardInfo.setBaseData(500);
        simcardInfo.setMsisdn("13911111111");
        simcardInfo.setSmsCount(100);
        simcardInfo.setName("动感地带");

        System.out.println("寻找自己需要的套餐:" + simcardInfo);
        return simcardInfo;
    }

    @Override
    public void buy(Callback callback) {
        if (callback==null || callback.getSimcardInfo()==null) throw new RuntimeException("号都没选号，买个锤子啊");

        callback.setActive(true);
        System.out.println("购买并激活成功");
    }

    @Override
    public void orderPackage(Callback callback) {
        if (callback==null || !callback.isActive()) throw new RuntimeException("还没激活成功，办个锤子套餐啊");

        System.out.println("购买流量套餐和宽带");
    }
}
