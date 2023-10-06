package com.zjut.study.tdengine.boot.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("weather")
public class WeatherDO {

    @TableId
    private Timestamp ts;
    private Float temperature;
    private Float humidity;
    private String note;
    private String location;
}
