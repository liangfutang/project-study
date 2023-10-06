package com.zjut.study.tdengine.boot.service;

import com.zjut.study.tdengine.boot.domain.WeatherDO;

public interface WeatherService {

    /**
     * 初始化时序库，创建库、表、测试数据
     * @return true:初始化成功  false:失败
     */
    Integer init();

    /**
     * 根据城市id查询当前城市最新的一条天气数据
     * @param cityId
     * @return
     */
    WeatherDO lastOne(Integer cityId);
}
