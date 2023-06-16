package com.zjut.study;

import com.zjut.study.transformer.FruitTransformer;

import java.lang.instrument.Instrumentation;

/**
 * 简单的计算执行耗时
 * @author tlf
 */
public class ChangeCode {

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("hello agent!");
        // 方式一
        instrumentation.addTransformer(new FruitTransformer());

        // 方式二
//        String fileName="F:\\Workspace\\Fruit2.class";
//        ClassDefinition def=new ClassDefinition(Fruit.class,
//                FruitTransformer.getClassBytes(fileName));
//        instrumentation.redefineClasses(new ClassDefinition[]{def});
    }

}
