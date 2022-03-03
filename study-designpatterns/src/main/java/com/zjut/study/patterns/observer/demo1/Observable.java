package com.zjut.study.patterns.observer.demo1;

/**
 * 被观察者的一些行为
 */
public interface Observable {

    /**
     * 添加一个观察者
     */
    void addObserver(Observer observer);

    /**
     * 删除一个观察者
     */
    void removeObserver(Observer observer);

    /**
     * 通知所有的观察者消息
     * @param context
     */
    void notifyAllObserver(String context);
}
