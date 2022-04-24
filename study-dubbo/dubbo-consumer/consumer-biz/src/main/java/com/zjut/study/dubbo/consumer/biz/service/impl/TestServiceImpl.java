package com.zjut.study.dubbo.consumer.biz.service.impl;

import com.zjut.study.dubbo.consumer.biz.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public String test() {
        return "success";
    }
}
