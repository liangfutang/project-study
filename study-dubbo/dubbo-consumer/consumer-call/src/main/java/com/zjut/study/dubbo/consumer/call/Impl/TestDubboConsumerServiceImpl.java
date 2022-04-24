package com.zjut.study.dubbo.consumer.call.Impl;

import com.zjut.study.dubbo.consumer.call.TestDubboConsumerService;
import com.zjut.study.dubbo.provider.client.service.TestDubboService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestDubboConsumerServiceImpl implements TestDubboConsumerService {

    @Reference(version = "1.0.0", check = false)
    private TestDubboService testDubboService;


    @Override
    public String test(String name) {
        log.info("即将请求provider");
        return testDubboService.test(name);
    }
}
