package com.zjut.study.spring.aop.simpleuse;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {

    @Pointcut("execution(* com.zjut.study.spring.aop.simpleuse.PeopleService.*(..))")
    private void pointCut() {}

    @Before("pointCut()")
    public void doBefore() {
        System.out.println("加载在一个人干活之前的动作");
    }
}
