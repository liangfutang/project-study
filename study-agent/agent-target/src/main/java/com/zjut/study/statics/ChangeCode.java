package com.zjut.study.statics;

import com.zjut.study.model.Fruit;

/**
 * 实现字节码替换
 * @author tlf
 */
public class ChangeCode {

    public static void main(String[] args) {
        Fruit fruit = new Fruit();
        fruit.getFruit();
    }
}
