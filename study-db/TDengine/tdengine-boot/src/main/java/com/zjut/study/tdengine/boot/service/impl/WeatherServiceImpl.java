package com.zjut.study.tdengine.boot.service.impl;

import com.zjut.study.tdengine.boot.dao.WeatherMapper;
import com.zjut.study.tdengine.boot.domain.WeatherDO;
import com.zjut.study.tdengine.boot.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherMapper weatherMapper;

    @Override
    public WeatherDO lastOne(Integer cityId) {
        return weatherMapper.lastOne(cityId);
    }
}
