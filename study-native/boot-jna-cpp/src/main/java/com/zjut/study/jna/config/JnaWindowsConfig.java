package com.zjut.study.jna.config;

import com.zjut.study.common.constants.Constants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 部署到windows时将jna对象注入到spring容器中
 */
@Configuration
@ConditionalOnProperty(value = "jna.os.name", havingValue = Constants.WINDOWS)
public class JnaWindowsConfig {

}
