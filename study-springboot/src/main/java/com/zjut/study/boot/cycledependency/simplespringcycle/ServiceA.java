//package com.zjut.study.boot.cycledependency.simplespringcycle;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ServiceA {
//
//    @Autowired
//    private ServiceB serviceB;
//
//    public void getB() {
//        System.out.println("获取B");
//        serviceB.getA();
//    }
//}
