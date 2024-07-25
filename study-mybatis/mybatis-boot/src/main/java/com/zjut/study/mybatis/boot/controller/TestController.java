package com.zjut.study.mybatis.boot.controller;

import com.zjut.study.mybatis.boot.service.IsolationTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jack
 */
@RestController
public class TestController {

    @Resource
    private IsolationTestService isolationTestService;
    @GetMapping("/test")
    public String test() {
        return "success";
    }
}
