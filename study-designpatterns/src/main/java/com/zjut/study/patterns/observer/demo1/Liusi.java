package com.zjut.study.patterns.observer.demo1;

/**
 * 其中一个观察者：刘斯
 */
public class Liusi implements Observer{
    @Override
    public void update(String context) {
        System.out.println("刘斯: 观察到韩非子的行动，开始像老板汇报");
        this.report2boss(context);
        System.out.println("刘斯: 像老板汇报结束");
    }

    /**
     * 向老板汇报
     * @param reportContext
     */
    private void report2boss(String reportContext) {
        System.out.println("刘斯: 老板,"+ reportContext);
    }
}
