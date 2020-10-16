//package com.zjut.study.boot.cycledependency.simplespringcycle;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ServiceB {
//
//    @Autowired
//    private ServiceA serviceA;
//
//    public void getA() {
//        System.out.println("获取A");
//        serviceA.getB();
//    }
//}
