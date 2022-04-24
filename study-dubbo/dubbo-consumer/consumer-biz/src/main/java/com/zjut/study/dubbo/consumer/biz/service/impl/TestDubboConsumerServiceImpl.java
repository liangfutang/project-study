package com.zjut.study.dubbo.consumer.biz.service.impl;

import com.zjut.study.dubbo.consumer.biz.service.TestDubboConsumerService;
import com.zjut.study.dubbo.provider.client.service.TestDubboService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class TestDubboConsumerServiceImpl implements TestDubboConsumerService {

    @Reference(version = "1.0.0")
    private TestDubboService testDubboService;


    @Override
    public String test(String name) {
        return testDubboService.test(name);
    }
}
