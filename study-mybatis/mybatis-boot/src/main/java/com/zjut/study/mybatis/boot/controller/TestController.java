package com.zjut.study.mybatis.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.mybatis.boot.mapper.IsolationTestMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jack
 */
@RestController
public class TestController {

    @Resource
    private IsolationTestMapper isolationTestMapper;
    @GetMapping("/test")
    public String test() {
        Object o = isolationTestMapper.selectById(1);
        System.out.println(JSONObject.toJSONString(o));
        return "success";
    }
}
