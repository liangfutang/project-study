package com.zjut.study.patterns.observer.demo1;

public class Client {
    public static void main(String[] args) {
        // 定义观察者
        Observer lisi = new Lisi();
        Observer liusi = new Liusi();
        Observer wangsi = new Wangsi();
        // 定义被观察者
        HanFeiZi hanfeizi = new HanFeiZi();
        hanfeizi.addObserver(lisi);
        hanfeizi.addObserver(liusi);
        hanfeizi.addObserver(wangsi);

        // 韩非子吃早饭
        hanfeizi.haveBreakFast();
    }
}
