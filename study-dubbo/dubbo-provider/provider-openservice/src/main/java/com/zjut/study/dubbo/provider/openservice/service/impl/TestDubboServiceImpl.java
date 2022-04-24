package com.zjut.study.dubbo.provider.openservice.service.impl;

import com.zjut.study.dubbo.provider.client.service.TestDubboService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0.0")
@Slf4j
public class TestDubboServiceImpl implements TestDubboService {
    @Override
    public String test(String name) {
        log.info("进入到provider...");
        return StringUtils.isBlank(name) ? "success" : name;
    }
}
