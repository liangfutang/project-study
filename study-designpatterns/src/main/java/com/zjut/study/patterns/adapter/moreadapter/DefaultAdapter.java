package com.zjut.study.patterns.adapter.moreadapter;

/**
 * 中间添加一层，帮助实现一些默认的方法，避免有的转换器不需要的转换，但是又不得不实现该方法
 */
public abstract class DefaultAdapter<T> implements TargetDC2{

    /**
     * 将输入源抽到父类中
     */
    private final T t;

    public T getT() {
        return t;
    }

    public DefaultAdapter (T t) {
        this.t = t;
    }

    @Override
    public int outputDC5() {
        throw new UnsupportedOperationException("当前未做此转换，禁止做该类动作");
    }

    @Override
    public int outputDC10() {
        throw new UnsupportedOperationException("当前未做此转换，禁止做该类动作");
    }

    @Override
    public int outputDC220() {
        throw new UnsupportedOperationException("当前未做此转换，禁止做该类动作");
    }
}
