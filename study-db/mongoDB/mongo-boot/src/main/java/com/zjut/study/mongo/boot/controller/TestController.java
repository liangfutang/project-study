package com.zjut.study.mongo.boot.controller;

import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import com.zjut.study.mongo.boot.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/check")
    public Result<?> test() {
        return Results.success(testService.check());
    }
}
