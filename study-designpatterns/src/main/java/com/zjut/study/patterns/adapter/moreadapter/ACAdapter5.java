package com.zjut.study.patterns.adapter.moreadapter;

/**
 * 5V转换器
 */
public class ACAdapter5 extends DefaultAdapter<OriginAC2>{


    public ACAdapter5(OriginAC2 originAC2) {
        super(originAC2);
    }


    @Override
    public int outputDC5() {
        int i = getT().outputAC();
        // 处理中间业务
        return i/44;
    }
}
