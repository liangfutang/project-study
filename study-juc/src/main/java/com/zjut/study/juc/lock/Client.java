package com.zjut.study.juc.lock;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client extends CommonJunitFilter {

    @Test
    public  void test01() {
        Lock lock = new ReentrantLock();
    }

}
