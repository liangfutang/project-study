package com.zjut.study.dubbo.consumer.controller;

import com.zjut.study.dubbo.consumer.call.TestDubboConsumerService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.info("进入consumer请求");
        return testDubboConsumerService.test(name);
    }
}
