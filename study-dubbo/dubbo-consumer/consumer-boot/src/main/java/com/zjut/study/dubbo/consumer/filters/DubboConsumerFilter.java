package com.zjut.study.dubbo.consumer.filters;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

import java.util.Objects;

/**
 * 用traceID串联整个请求链路
 */
@Slf4j
public class DubboConsumerFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation inv) throws RpcException {
        long start = System.currentTimeMillis();
        log.info("dubbo消费者:{}.{}, 入参arguments:{}",invoker.getInterface().getSimpleName(), inv.getMethodName(), JSONObject.toJSONString(inv.getArguments()));
        Result result;
        try {
            result = invoker.invoke(inv);
        } catch (Exception e) {
            log.error("rpc invoke error! ",e);
            throw e;
        }

        if (result.hasException()) {
            log.error("dubbo消费异常:", result.getException());
        }
        long end = System.currentTimeMillis();
        Object value = result.getValue();
        log.info("dubbo消费者收到结果:{},耗时:{}", Objects.isNull(value) ? "":JSONObject.toJSONString(value), (end-start));
        return result;
    }
}
