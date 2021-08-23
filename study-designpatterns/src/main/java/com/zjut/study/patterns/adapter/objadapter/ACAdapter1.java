package com.zjut.study.patterns.adapter.objadapter;

public class ACAdapter1 implements TargetDC1 {

    private final OriginAC1 originAC1;

    public ACAdapter1(OriginAC1 originAC1) {
        this.originAC1 = originAC1;
    }

    @Override
    public int outputDC() {
        int i = originAC1.outputAC();
        // 处理一些乱七八糟的逻辑
        return i/44;
    }
}
