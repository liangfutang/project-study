package com.zjut.study.patterns.observer.demo1;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者：韩非子
 */
public class HanFeiZi implements IHanFeiZi, Observable {
    private List<Observer> observerList;
    public HanFeiZi() {
        observerList = new ArrayList<>();
    }

    @Override
    public void haveBreakFast() {
        System.out.println("韩非子：开始吃吃早饭了");
        this.notifyAllObserver("韩非子在吃早饭");
    }

    @Override
    public void haveFun() {
        System.out.println("韩非子：开始吃午饭了");
        this.notifyAllObserver("韩非子在吃午饭");
    }

    // =========

    @Override
    public void addObserver(Observer observer) {
        this.observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observerList.remove(observer);
    }

    @Override
    public void notifyAllObserver(String context) {
        // 向每个监听者发送消息
        for (Observer one : observerList) {
            one.update(context);
        }
    }
}
