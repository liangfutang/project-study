package com.zjut.study.tdengine.boot.dao;

import com.zjut.study.tdengine.boot.domain.WeatherDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WeatherMapper {
    WeatherDO lastOne(Integer cityId);

}
