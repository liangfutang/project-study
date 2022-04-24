package com.zjut.study.dubbo.consumer.controller;

import com.zjut.study.dubbo.consumer.call.TestDubboConsumerService;
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
    private TestDubboConsumerService testDubboConsumerService;

    /**
     * 请求dubbo接口
     * 将传入的参数直接透传回来
     * @param name
     * @return
     */
    @GetMapping("/dubbo")
    public String dubbo(@RequestParam String name) {
        return testDubboConsumerService.test(name);
    }
}
