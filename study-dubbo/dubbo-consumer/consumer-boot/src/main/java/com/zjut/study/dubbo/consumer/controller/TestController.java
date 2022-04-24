package com.zjut.study.dubbo.consumer.controller;

import com.zjut.study.dubbo.consumer.call.TestDubboConsumerService;
import com.zjut.study.dubbo.consumer.biz.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService testService;
    @Resource
    private TestDubboConsumerService testDubboConsumerService;

    @GetMapping("/trace")
    public String test() {
        return testService.test();
    }

    @GetMapping("/dubbo")
    public String dubbo(@RequestParam String name) {
        return testDubboConsumerService.test(name);
    }
}
