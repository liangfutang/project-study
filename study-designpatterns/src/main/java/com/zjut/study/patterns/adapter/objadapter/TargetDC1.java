package com.zjut.study.patterns.adapter.objadapter;

/**
 * 最终目标需要的电压
 * 当前需要输出一个5V的
 */
public interface TargetDC1 {

    /**
     * 目标需要输出的
     * @return
     */
    int outputDC();
}
