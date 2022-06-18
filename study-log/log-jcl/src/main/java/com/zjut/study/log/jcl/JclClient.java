package com.zjut.study.log.jcl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.junit.Test;

/**
 * @author jack
 * jcl是日志的门面，没有引用其他实现的时候默认使用jul
 */
public class JclClient {

    /**
     * jcl只是门面
     *
     * 在只引用 commons-logging 的jcl包时，默认使用的是jul的实现，所以会打印出如下红色的:
     * Connected to the target VM, address: '127.0.0.1:62083', transport: 'socket'
     * 六月 19, 2022 3:44:09 上午 com.zjut.study.log.jcl.JclClient log
     * 信息: info log
     * 六月 19, 2022 3:44:09 上午 com.zjut.study.log.jcl.JclClient log
     * 警告: warn log
     * 六月 19, 2022 3:44:09 上午 com.zjut.study.log.jcl.JclClient log
     * 严重: error log
     * 六月 19, 2022 3:44:09 上午 com.zjut.study.log.jcl.JclClient log
     * 严重: fatal log
     * Disconnected from the target VM, address: '127.0.0.1:62083', transport: 'socket'
     *
     * 在引用 log4j 包的时候是使用会切换成log4j输出日志，如没有配置文件会报找不到appender的错，加入了配置文件后就能正常的用log4j输出了
     *
     *
     * 核心原理：
     * LogFactory.getLog
     * @see LogFactory#getFactory()  创建LogFactory实例(org.apache.commons.logging.impl.LogFactoryImpl)
     * @see LogFactoryImpl#getInstance(java.lang.String) 创建Log实例
     * @see LogFactoryImpl#discoverLogImplementation(java.lang.String) 循环尝试为给定的类别名称创建 Log 实例
     *    其中classesToDiscover数组作为日志适配器的类的名称
     * @see LogFactoryImpl#createLogFromClass(java.lang.String, java.lang.String, boolean) 尝试加载给定的类并实例化Log的实例
     */
    @Test
    public void log() {
        Log log = LogFactory.getLog(JclClient.class);

        log.trace("trace log");
        log.debug("debug log");
        log.info("info log");
        log.warn("warn log");
        log.error("error log");
        log.fatal("fatal log");
    }
}
