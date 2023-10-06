package com.zjut.study.mongo.boot.service.impl;

import com.zjut.study.mongo.boot.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public String check() {
        return "success";
    }
}
