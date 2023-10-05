package com.zjut.study.tdengine.boot.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Map;

/**
 * 拦截官方提供的mapper接口
 */
@Aspect
@Component
public class TaosAspect {

    @Around("execution(java.util.Map<String,Object> com.zjut.study.tdengine.boot.dao.WeatherMapper.*(..))")
    public Object handleType(ProceedingJoinPoint joinPoint) {
        Map<String, Object> result = null;
        try {
            result = (Map<String, Object>) joinPoint.proceed();
            for (String key : result.keySet()) {
                Object obj = result.get(key);
                if (obj instanceof byte[]) {
                    obj = new String((byte[]) obj);
                    result.put(key, obj);
                }
                if (obj instanceof Timestamp) {
                    obj = ((Timestamp) obj).getTime();
                    result.put(key, obj);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }
}
