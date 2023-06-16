package com.zjut.study;

import com.zjut.study.transformer.FruitTransformer;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class RetransformAgent {

    public static void agentmain(String agentArgs, Instrumentation inst)
            throws UnmodifiableClassException {
        inst.addTransformer(new FruitTransformer(),true);
        // 方式一
        //transform是会对尚未加载的类进行增加代理层，这里是已经运行中的jvm，所以类以及被加载了
        //必须主动调用retransformClasses让jvm再对运行中的类进行加上代理层
        for (Class<?> allLoadedClass : inst.getAllLoadedClasses()) {
            if(allLoadedClass.getName().contains("com.zjut.study.boot.agent.Fruit")){
                try {
                    inst.retransformClasses(allLoadedClass);
                } catch (UnmodifiableClassException e) {
                    e.printStackTrace();
                }
            }
        }
//        inst.retransformClasses(Fruit.class);
        System.out.println("retransform success");
    }
}
