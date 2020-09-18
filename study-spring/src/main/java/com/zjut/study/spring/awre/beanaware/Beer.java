package com.zjut.study.spring.awre.beanaware;

public class Beer {

    private Bread bread;

    public Beer() {
        System.out.println("构建Beer....");
    }

    public void drinkBeer() {
        System.out.println("方法中---小王爱喝啤酒:" + bread.eatWay(null));
    }

    /**
     * 测试入住属性时是通过setter方式注入的
     * @param bread
     */
    public void setBread(Bread bread) {
        this.bread = bread;
        System.out.println("通过setter方式注入");
    }

    public void init() {
        System.out.println("初始化Beer");
    }

    public void destory() {
        System.out.println("销毁Beer");
    }
}
