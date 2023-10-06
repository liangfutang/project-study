package com.zjut.study.common.convention.result;

import com.zjut.study.common.convention.code.ServiceCode;

/**
 * 分页查询结果封装
 * @author tlf
 */
public class DefaultPageResult<T> extends DefaultResult<T> implements PageResult<T> {
    /**
     * 总数
     */
    private Integer total = 0;

    @Override
    public DefaultPageResult<T> setTotal(Integer total) {
        this.total = total;
        return this;
    }

    @Override
    public Integer getTotal() {
        return total;
    }

    public DefaultPageResult(T data, Integer code, String message, Integer total) {
        super.setData(data);
        super.setCode(code);
        super.setMessage(message);
        this.total = total;
    }

    public DefaultPageResult(T data, Integer code, String message) {
        super.setData(data);
        super.setCode(code);
        super.setMessage(message);
    }

    public DefaultPageResult(T data, ServiceCode serviceCode) {
        super.setCode(serviceCode.getCode());
        super.setMessage(serviceCode.getMessage());
        super.setData(data);
    }

    public DefaultPageResult(T data, ServiceCode serviceCode, Integer total) {
        super.setCode(serviceCode.getCode());
        super.setMessage(serviceCode.getMessage());
        super.setData(data);
        this.total = total;
    }

    public DefaultPageResult(ServiceCode serviceCode) {
        super.setCode(serviceCode.getCode());
        super.setMessage(serviceCode.getMessage());
    }

    public DefaultPageResult(ServiceCode serviceCode, Integer total) {
        super.setCode(serviceCode.getCode());
        super.setMessage(serviceCode.getMessage());
        this.total = total;
    }
}
