package com.zjut.study.tdengine.boot.service;

import com.zjut.study.tdengine.boot.domain.WeatherDO;

public interface WeatherService {

    /**
     * 根据城市id查询当前城市最新的一条天气数据
     * @param cityId
     * @return
     */
    WeatherDO lastOne(Integer cityId);
}
