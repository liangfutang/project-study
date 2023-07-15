package com.zjut.study.controller;

import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import com.zjut.study.properties.DemoProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 提供测试接口
 */
@RestController
@RequestMapping("properties")
public class TestController {

    @Resource
    private DemoProperties demoProperties;

    @GetMapping("/demo")
    public Result<?> selectDemoProperties() {
        return Results.success(demoProperties.toString());
    }
}
