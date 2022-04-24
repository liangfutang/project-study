package com.zjut.study.dubbo.consumer.filters;

import com.zjut.study.common.constants.Constants;
import com.zjut.study.common.utils.TraceIdUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用traceID串联整个请求链路
 * @author liangfu.tang
 */
public class TraceIdFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(TraceIdFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        String traceId = request.getHeader(Constants.TRACE_ID);
        try {
            if (StringUtils.isBlank(traceId)) {
                traceId = TraceIdUtil.generateTraceId();
                logger.info("服务生成的traceId:" + traceId + "!请求开始:" + request.getRequestURI());
            } else {
                TraceIdUtil.setTraceId(traceId);
                logger.info("网关带来的traceId:" + traceId + "!请求开始:" + request.getRequestURI());
            }
        } catch (Exception e) {
            logger.error("生成traceID异常:", e);
        }

        filterChain.doFilter(request, response);

        long end = System.currentTimeMillis();
        logger.info("本次请求时长:" + (end-start));
        try {
            TraceIdUtil.removeTraceId();
        } catch (Exception e) {
            logger.error("移除traceID异常:", e);
        }
    }
}
