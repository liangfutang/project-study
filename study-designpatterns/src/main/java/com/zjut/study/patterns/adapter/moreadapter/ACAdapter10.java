package com.zjut.study.patterns.adapter.moreadapter;

/**
 * 10V转换器
 */
public class ACAdapter10 extends DefaultAdapter<OriginAC2>{

    public ACAdapter10(OriginAC2 originAC2) {
        super(originAC2);
    }

    @Override
    public int outputDC10() {
        int i = getT().outputAC();
        // 处理中间业务
        return i/22;
    }
}
