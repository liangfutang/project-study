package com.zjut.study.boot.controller;

import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 做路径相关的测试
 * @author jack
 */
@RestController
@RequestMapping("/path")
public class PathController {

    /**
     * 指定路径中的类型必须是数值类型的
     * @param id
     * @return
     */
    @GetMapping("/data/{id:\\d+}")
    public Result<?> testPath(@PathVariable String id) {
        return Results.success(id);
    }
}
