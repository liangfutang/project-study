package com.zjut.study.log.jul;

import org.junit.Test;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author jack
 * 不需要引用包
 */
public class JulClient {

    /**
     * 只打印出info以上级别日志，默认的是info级别。除非父子log都调整级别才会打印到最高的那一层
     *
     * 打印出的是红色的日志，因为内部使用的是System.err
     * @see ConsoleHandler#publish(java.util.logging.LogRecord) 中使用writer.write(getFormatter().getHead(this));打印日志，
     *      而writer是在ConsoleHandler的构造函数中设置的，setOutputStream(System.err);设置的输出流失err输出流
     */
    @Test
    public void allLevel() {
        Logger logger = Logger.getLogger(JulClient.class.getName());

        // java.util.logging.LogManager$RootLogger@56235b8e 父log是根log
        System.out.println(logger.getParent());

        // 子log的handler此时是0个
        System.out.println(logger.getHandlers().length);

        // INFO 父log默认使用的级别
        System.out.println(logger.getParent().getLevel());

        // 因为子log没有设置想应的handler，用的是父log，所以需要将父log和父handler设置成对应的级别才会打印出设置的最高级别的那个
        logger.setLevel(Level.ALL);
        Handler[] handlers = logger.getParent().getHandlers();
        for (Handler handler : handlers) {
            handler.setLevel(Level.ALL);
        }

        logger.log(Level.ALL, "all level");
        logger.log(Level.FINEST, "finest level");
        logger.log(Level.FINER, "finer level");
        logger.log(Level.FINE, "fine level");
        logger.log(Level.CONFIG, "config level");
        logger.log(Level.INFO, "info level");

        logger.log(Level.WARNING, "waring-1 level");
        logger.warning("waring-2 level");

        logger.log(Level.SEVERE, "server level");
    }

    /**
     * 通过log名字中的 . 来决定父子关系
     */
    @Test
    public void relation() {
        // 所有Log的默认父级是java.util.logging.LogManager$RootLogger
        Logger logger1 = Logger.getLogger("a");
        Logger logger2 = Logger.getLogger("a.b");
        Logger logger3 = Logger.getLogger("a.b.c");
        // a.b
        System.out.println(logger3.getParent().getName());
        // true
        System.out.println(logger3.getParent() == logger2);
        System.out.println(logger2.getParent() == logger1);
    }

    /**
     * 设置自己的handler
     * 如果不限制父级handler，则会打印两编日志
     */
    @Test
    public void selfHandler() {
        Logger logger = Logger.getLogger("a");

        ConsoleHandler consoleHandler = new ConsoleHandler();
        // 设置自己的handler,并未该handler设置级别等相关参数属性
        logger.addHandler(consoleHandler);
        // 如果不设置将会打印两遍日志
        logger.setUseParentHandlers(false);
        logger.info("self handler");
        // 1
        System.out.println(logger.getHandlers().length);
    }

    /**
     * 使用配置文件做相关配置，将${JAVA_HOME}/jre/lib/logging.properties复制过来做项目的日志配置
     */
    @Test
    public void loggingProperties() throws IOException {
        LogManager logManager = LogManager.getLogManager();
        logManager.readConfiguration(JulClient.class.getClassLoader().getResourceAsStream("logging.properties"));
        // com.zjut.study.log.jul.JulClient
        System.out.println(JulClient.class.getName());

        Logger logger = Logger.getLogger(JulClient.class.getName());

        // 根据命名规则，如果配置文件中没有父级，则使用默认的父级log level，如有则已自定义父级的 lever为准
        // 在log的lever和handler的level中取最高级别以上的日志打印
        logger.log(Level.ALL, "all level");
        logger.log(Level.FINEST, "finest level");
        logger.log(Level.FINER, "finer level");
        logger.log(Level.FINE, "fine level");
        logger.log(Level.CONFIG, "config level");
        logger.log(Level.INFO, "info level");
        logger.log(Level.WARNING, "waring-1 level");
        logger.log(Level.SEVERE, "server level");
    }

}
