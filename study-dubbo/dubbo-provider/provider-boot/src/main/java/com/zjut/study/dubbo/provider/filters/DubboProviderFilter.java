package com.zjut.study.dubbo.provider.filters;

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
 * @author liangfu.tang
 */
@Slf4j
public class DubboProviderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation inv) throws RpcException {
        long start = System.currentTimeMillis();
        log.info("dubbo提供者:{}.{}, 入参arguments:{}",invoker.getInterface().getSimpleName(), inv.getMethodName(), JSONObject.toJSONString(inv.getArguments()));
        Result result = invoker.invoke(inv);
        Throwable throwable = result.getException();
        if (throwable != null) {
            log.error("dubbo请求异常! ",throwable);
        }
        long end = System.currentTimeMillis();
        Object value = result.getValue();
        log.info("dubbo提供者将返回:{},耗时:{}", Objects.isNull(value) ? "":JSONObject.toJSONString(result.getValue()), (end-start));
        return result;
    }
}
