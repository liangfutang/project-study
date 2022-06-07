package com.zjut.study.mybatis.boot.properties;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库配置
 * @author liangfu.tang
 */
@Data
@Configuration
public class DataSourceProperties {

    private String url = "jdbc:mysql://127.0.0.1:3306/private_test?characterEncoding=utf-8&useUnicode=true&serverTimezone=UTC";
    private String username = "root";
    private String password = "123456";
    private String driverClass = "com.mysql.jdbc.Driver";
    private String filters = "stat,wall,log4j";
    private int maxActive = 20;
    private int initialSize = 1;
    private long maxWait = 60000;
    private int minIdle = 1;
    private long timeBetweenEvictionRunsMillis = 60000;
    private long minEvictableIdleTimeMillis = 300000;
    private String validationQuery = "select 'x'";
    private Boolean testWhileIdle = true;
    private Boolean testOnBorrow = false;
    private Boolean testOnReturn = false;
    private Boolean poolPreparedStatements = false;
    private int maxOpenPreparedStatements = 20;
    private long timeBetweenLogStatsMillis = 300000;
}
