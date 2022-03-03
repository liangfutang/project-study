package com.zjut.study.patterns.observer.demo1;

/**
 * 观察者
 */
public interface Observer {

    /**
     * 观察者在观察到被观察者有所动作后的行动
     * @param context
     */
    void update(String context);
}
