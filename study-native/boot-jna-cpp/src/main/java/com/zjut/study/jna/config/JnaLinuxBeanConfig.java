package com.zjut.study.jna.config;

import com.zjut.study.common.constants.Constants;
import com.zjut.study.jna.SolveService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 部署到Linux时将jna对象注入到spring容器中
 */
@Configuration
@ConditionalOnProperty(value = "jna.os.name", havingValue = Constants.LINUX)
public class JnaLinuxBeanConfig {

    @Bean
    public SolveService libgetGSV() {
        System.out.println("加载 LibgetGSV");
        return SolveService.INSTANCE;
    }

}
