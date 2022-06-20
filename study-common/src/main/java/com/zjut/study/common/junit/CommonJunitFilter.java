package com.zjut.study.common.junit;

import org.junit.After;
import org.junit.Before;

/**
 * 每个测试方法的前后执行的方法
 * @author jack
 */
public class CommonJunitFilter {
    @Before
    public void beforeAll() {
        System.out.println("开始...\n");
        // 代理类class文件存入本地磁盘方便我们反编译查看源码
//      System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\code");
    }

    @After
    public void afterAll() {
        System.out.println("\n结束...");
    }
}
