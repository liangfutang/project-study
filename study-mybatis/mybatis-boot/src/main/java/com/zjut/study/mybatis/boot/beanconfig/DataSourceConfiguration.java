package com.zjut.study.mybatis.boot.beanconfig;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.zjut.study.mybatis.boot.properties.DataSourceProperties;
import com.zjut.study.mybatis.boot.properties.DruidProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库相关配置
 * @author liangfu.tang
 */
@Configuration
@Slf4j
public class DataSourceConfiguration {

    private final String MAPPER_LOCATIONS = "classpath:mapper/*.xml";

    @Autowired
    private DruidProperties druidProperties;
    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dataSourceProperties.getDriverClass());
        dataSource.setMaxActive(dataSourceProperties.getMaxActive());
        dataSource.setInitialSize(dataSourceProperties.getInitialSize());
        dataSource.setMaxWait(dataSourceProperties.getMaxWait());
        dataSource.setMinIdle(dataSourceProperties.getMinIdle());
        dataSource.setTimeBetweenEvictionRunsMillis(dataSourceProperties.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(dataSourceProperties.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(dataSourceProperties.getValidationQuery());
        dataSource.setTestWhileIdle(dataSourceProperties.getTestWhileIdle());
        dataSource.setTestOnBorrow(dataSourceProperties.getTestOnBorrow());
        dataSource.setTestOnReturn(dataSourceProperties.getTestOnReturn());
        dataSource.setPoolPreparedStatements(dataSourceProperties.getPoolPreparedStatements());
        dataSource.setMaxOpenPreparedStatements(dataSourceProperties.getMaxOpenPreparedStatements());
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setTimeBetweenLogStatsMillis(dataSourceProperties.getTimeBetweenLogStatsMillis());

        List<Filter> filters = new ArrayList<>();
        StatFilter statFilter = new StatFilter();
        statFilter.setDbType(druidProperties.getDbType());
        statFilter.setLogSlowSql(druidProperties.isLogSlowSql());
        statFilter.setSlowSqlMillis(druidProperties.getSlowSqlMillis());
        statFilter.setMergeSql(druidProperties.isMergeSql());
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        slf4jLogFilter.setConnectionLoggerName(druidProperties.getConnectionLoggerName());
        slf4jLogFilter.setDataSourceLoggerName(druidProperties.getDataSourceLoggerName());
        slf4jLogFilter.setResultSetLoggerName(druidProperties.getResultSetLoggerName());
        slf4jLogFilter.setStatementLoggerName(druidProperties.getStatementLoggerName());
        slf4jLogFilter.setStatementLogEnabled(druidProperties.getStatementExecutableSqlLogEnable());
        WallFilter wallFilter = new WallFilter();
        wallFilter.setDbType(druidProperties.getDbType());
        wallFilter.setLogViolation(druidProperties.getLogViolation());
        wallFilter.setThrowException(druidProperties.getThrowException());
        filters.add(statFilter);
        filters.add(slf4jLogFilter);
        filters.add(wallFilter);
        dataSource.setProxyFilters(filters);

        try {
            dataSource.setFilters(dataSourceProperties.getFilters());
            dataSource.init();
            //设置批量更新
            wallFilter.getConfig().setMultiStatementAllow(druidProperties.getMultiStatementAllow());
        } catch (SQLException e) {
            log.error("数据库设置批量更新异常", e);
        }
        return dataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean mybatisSqlSessionFactoryBean = new SqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MAPPER_LOCATIONS));
        return mybatisSqlSessionFactoryBean.getObject();
    }
//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        return interceptor;
//    }

    @Bean
//    @ConditionalOnProperty(name = "druid.stat-view-servlet.enabled", havingValue = "true")
    public ServletRegistrationBean<?> servletRegistrationBean() {
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet());
        registrationBean.addUrlMappings(druidProperties.getStatViewUrlPattern() != null ? druidProperties.getStatViewUrlPattern() : "/druid/*");
        if (druidProperties.getAllow() != null) {
            registrationBean.addInitParameter("allow", druidProperties.getAllow());
        }
        if (druidProperties.getDeny() != null) {
            registrationBean.addInitParameter("deny", druidProperties.getDeny());
        }
        if (druidProperties.getLoginUsername() != null) {
            registrationBean.addInitParameter("loginUsername", druidProperties.getLoginUsername());
        }
        if (druidProperties.getLoginPassword() != null) {
            registrationBean.addInitParameter("loginPassword", druidProperties.getLoginPassword());
        }
        if (druidProperties.getResetEnable() != null) {
            registrationBean.addInitParameter("resetEnable", druidProperties.getResetEnable());
        }
        return registrationBean;
    }


    @Bean
//    @ConditionalOnProperty(name = "druid.web-stat-filter.enabled", havingValue = "true")
    public FilterRegistrationBean<?> filterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> registrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        registrationBean.addUrlPatterns(druidProperties.getWebStatUrlPattern() != null ? druidProperties.getWebStatUrlPattern() : "/*");
        registrationBean.addInitParameter("exclusions", druidProperties.getExclusions() != null ? druidProperties.getExclusions() : "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        if (druidProperties.getSessionStatEnable() != null) {
            registrationBean.addInitParameter("sessionStatEnable", druidProperties.getSessionStatEnable());
        }
        if (druidProperties.getSessionStatMaxCount() != null) {
            registrationBean.addInitParameter("sessionStatMaxCount", druidProperties.getSessionStatMaxCount());
        }
        if (druidProperties.getPrincipalSessionName() != null) {
            registrationBean.addInitParameter("principalSessionName", druidProperties.getPrincipalSessionName());
        }
        if (druidProperties.getPrincipalCookieName() != null) {
            registrationBean.addInitParameter("principalCookieName", druidProperties.getPrincipalCookieName());
        }
        if (druidProperties.getProfileEnable() != null) {
            registrationBean.addInitParameter("profileEnable", druidProperties.getProfileEnable());
        }
        return registrationBean;
    }
}
