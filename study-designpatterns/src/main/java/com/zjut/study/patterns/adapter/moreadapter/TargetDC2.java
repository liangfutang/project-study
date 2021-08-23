package com.zjut.study.patterns.adapter.moreadapter;

/**
 * 最终目标需要的电压
 * 当前需要输出一个5V的
 */
public interface TargetDC2 {

    /**
     * 目标需要输出的5v
     * @return
     */
    int outputDC5();

    /**
     * 目标需要输出的10v
     * @return
     */
    int outputDC10();

    /**
     * 目标需要输出的220v
     * @return
     */
    int outputDC220();
}
