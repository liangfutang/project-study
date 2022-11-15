package com.zjut.study.tdengine.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jack
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.zjut.study.tdengine.boot.dao"})
public class TdengineBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(TdengineBootApplication.class, args);
    }
}
