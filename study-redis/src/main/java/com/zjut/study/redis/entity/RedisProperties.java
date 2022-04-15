package com.zjut.study.redis.entity;

import lombok.Data;

/**
 * 连接redis的相关参数
 */
@Data
public class RedisProperties {
    private String host = "localhost";
    private Integer port = 6379;
    private Integer maxTotal = 1000;
    private Integer maxIdle = 32;
    private Integer maxWaitMillis = 100 * 1000;
    private Boolean testOnBorrow = true;
}
