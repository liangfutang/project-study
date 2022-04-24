package com.zjut.study.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class TraceIdUtil {
    private static final Logger logger = LoggerFactory.getLogger(TraceIdUtil.class);
    private static String SUFFIX = "";

    public TraceIdUtil() {
    }

    public static String getTraceId() {
        return MDC.get("traceId");
    }

    public static void setTraceId(String traceId) {
        MDC.put("traceId", traceId);
    }

    public static String generateTraceId() {
        String traceId = "";

        try {
            traceId = SUFFIX + UUID.randomUUID().toString().replaceAll("-", "");
        } catch (Exception var2) {
            logger.error("生成traceId失败", var2);
        }

        setTraceId(traceId);
        return traceId;
    }

    public static void removeTraceId() {
        MDC.clear();
    }

    static {
        try {
            InetAddress address = InetAddress.getLocalHost();
            String[] array = address.getHostAddress().split("\\.");
            SUFFIX = array[array.length - 1];
            logger.info("TraceIdUtil初始化获取ip的有效字符:{}", SUFFIX);
        } catch (UnknownHostException var2) {
            logger.error("生产traceID操作ip相关时异常:", var2);
        }

    }
}
