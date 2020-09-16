package com.zjut.study.spi.java;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;

import java.util.ServiceLoader;

/**
 * 测试java的spi
 */
public class Client extends CommonJunitFilter {

    @Test
    public void testJavaSpi() {
        ServiceLoader<SpiInterface> load = ServiceLoader.load(SpiInterface.class);
        for (SpiInterface one : load) {
            one.sayHi();
        }
    }

}
