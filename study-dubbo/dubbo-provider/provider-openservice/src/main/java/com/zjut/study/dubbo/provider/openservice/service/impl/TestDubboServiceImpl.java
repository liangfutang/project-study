package com.zjut.study.dubbo.provider.openservice.service.impl;

import com.zjut.study.dubbo.provider.client.service.TestDubboService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0.0")
public class TestDubboServiceImpl implements TestDubboService {
    @Override
    public String test(String name) {
        return StringUtils.isBlank(name) ? "success" : name;
    }
}
