package com.zjut.study.spi.java;


/**
 * 用作java的spi测试
 */
public class SpiInterfaceImpl1 implements SpiInterface {

    @Override
    public void sayHi() {
        System.out.println("java spi测试成功 1 ");
    }
}
