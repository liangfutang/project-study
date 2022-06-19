package com.zjut.study.log.slf4j.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jack
 */
public class Slf4jLogbackClient {

    /**
     * 默认会去类路径下去找名为logback.xml 配置文件，如找不到则会用juc
     */
    @Test
    public void log() {
        Logger logger = LoggerFactory.getLogger(Slf4jLogbackClient.class);

        logger.trace("trace log");
        logger.debug("debug log");
        logger.info("info log");
        logger.warn("warn log");
        logger.error("error log");
    }
}
