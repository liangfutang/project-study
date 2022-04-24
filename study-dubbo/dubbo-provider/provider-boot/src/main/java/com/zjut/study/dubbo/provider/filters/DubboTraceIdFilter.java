package com.zjut.study.dubbo.provider.filters;

import com.zjut.study.common.utils.TraceIdUtil;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

/**
 * 用traceID串联整个请求链路
 * @author liangfu.tang
 */
public class DubboTraceIdFilter implements Filter {
    private static final String TRACE_ID = "traceId";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation inv) throws RpcException {
        if (inv.getAttachment(TRACE_ID) != null) {
            // 提供者接收traceId
            TraceIdUtil.setTraceId(inv.getAttachment(TRACE_ID));
        } else {
            if (TraceIdUtil.getTraceId() == null) {
                // invocation和当前线程中都没有traceId,就生成一个
                TraceIdUtil.generateTraceId();
            }
            inv.getAttachments().put(TRACE_ID, TraceIdUtil.getTraceId());
        }
        return invoker.invoke(inv);
    }
}
