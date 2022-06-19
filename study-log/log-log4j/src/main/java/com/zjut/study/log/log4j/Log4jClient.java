package com.zjut.study.log.log4j;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author jack
 */
public class Log4jClient {

    /**
     * 使用log4j必须要有log4j的配置文件否则会报错
     * 默认会去类路径找log4j.properties文件，也支持xml文件格式
     */
    @Test
    public void noProperties() {
        /**
         * 如果没配置文件，所以会报如下错误:
         *
         * Connected to the target VM, address: '127.0.0.1:58983', transport: 'socket'
         * log4j:WARN No appenders could be found for logger (com.zjut.study.log.log4j.Log4jClient).
         * log4j:WARN Please initialize the log4j system properly.
         * log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
         * Disconnected from the target VM, address: '127.0.0.1:58983', transport: 'socket'
         */
        Logger logger = Logger.getLogger(Log4jClient.class);

        logger.trace("trace log");
        logger.debug("debug log");
        logger.info("info log");
        logger.warn("warn log");
        logger.error("error log");
        logger.fatal("fatal log");
    }
}
