package com.zjut.study.others.frequently;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * HashMap常用的一些容易忘记的方法
 * @author jack
 */
public class ConcurrentHashMapClient extends CommonJunitFilter {

    @Before
    public void init() {

    }

    @Test
    public void t() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
    }
}

