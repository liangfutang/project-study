package com.zjut.study.log.log4j.to.slf4j;

import com.zjut.study.log.log4j.Log4jClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jack
 */
public class Log4j2Slf4jClient {

    /**
     * 当前应用使用的是slf4j+logback,引入第三方包是log4j，需要将其桥接过来
     *
     * 在引入第三方包时需要排除第三方包中的日志配置文件 (提供第三方包时不能提供日志配置文件)
     * 本应用引入 <artifactId>log-log4j</artifactId> 模块，在测试本例时需要将配置文件排除
     */
    @Test
    public void log() {
        Logger logger = LoggerFactory.getLogger(Log4j2Slf4jClient.class);

        System.out.println("=====================当前应用日志 start========================");
        logger.trace("trace log");
        logger.debug("debug log");
        logger.info("info log");
        logger.warn("warn log");
        logger.error("error log");
        System.out.println("=====================当前应用日志 end========================");
        // 如果不引入桥接包，这里会红色报错 No appenders could be found for logger 并且不打印日志
        // 可以在logback pattern中随便加点东西，看打印出的日志样式
        System.out.println("=====================第三方应用日志 start========================");
        Log4jClient log4jClient = new Log4jClient();
        log4jClient.noProperties();
        System.out.println("=====================第三方应用日志 end========================");
    }
}
