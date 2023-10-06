package com.zjut.study.tdengine.boot.controller;

import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import com.zjut.study.tdengine.boot.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 官方提供的demo
 */
@RequestMapping("/weather")
@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    /**
     * 初始化数据库、超级表等信息
     * @return 初始化结果
     */
    @GetMapping("/init")
    public Result<Integer> init() {
        return Results.success(1);
    }

    /**
     * 通过城市id查找当前城市最新的一条天气数据
     * @param cityId 城市id
     * @return 该城市最新的一条天气数据
     */
    @GetMapping("/lastOne/{cityId}")
    public Result<?> lastOne(@PathVariable Integer cityId) {
        return Results.success(weatherService.lastOne(cityId));
    }
}
