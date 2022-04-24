package com.zjut.study.dubbo.provider.config;

import com.zjut.study.dubbo.provider.filters.TraceIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * 用作web相关的配置
 */
@Configuration
public class WebConfiguration {

    /**
     * 设置traceID的过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<?> traceIdFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TraceIdFilter());
        // 过滤路径
        registration.addUrlPatterns("/*");
        // 过滤器名称
        registration.setName("traceIdFilter");
        // 过滤器顺序
        registration.setOrder(2);
        return registration;
    }

}
