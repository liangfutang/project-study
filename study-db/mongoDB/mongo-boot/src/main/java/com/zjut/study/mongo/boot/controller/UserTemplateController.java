package com.zjut.study.mongo.boot.controller;

import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import com.zjut.study.mongo.boot.service.UserTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/template")
public class UserTemplateController {

    private final UserTemplateService userTemplateService;

    @PostMapping("/insert")
    public Result<?> insert() {
        return Results.success(userTemplateService.insert());
    }

    @GetMapping("/{id}")
    public Result<?> selectById(@PathVariable String id) {
        return Results.success(userTemplateService.selectById(id));
    }
}
