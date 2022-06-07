package com.zjut.study.mybatis.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jack
 */
@SpringBootApplication
@MapperScan("com/zjut/study/mybatis/boot/mapper")
public class MybatisBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisBootApplication.class, args);
    }
}
