package com.zjut.study.patterns.adapter.classadapter;

public class ACAdapter extends OriginAC implements TargetDC {
    @Override
    public int outputDC() {
        // 当前已经有了的电压
        int i = super.outputAC();
        // 经过一些乱七八糟的处理
        return i/44;
    }
}
