package com.zjut.study.tdengine.boot.dao;

import com.zjut.study.tdengine.boot.domain.WeatherDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WeatherMapper {

    Map<String, Object> lastOne();

    void dropDB();

    void createDB();

    void createSuperTable();

    void createTable(WeatherDO weather);

    List<WeatherDO> select(@Param("limit") Long limit, @Param("offset") Long offset);

    int insert(WeatherDO weather);

    int count();

    List<String> getSubTables();

    List<WeatherDO> avg();

}
