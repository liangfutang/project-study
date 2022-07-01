package com.zjut.study.common.convention.result;

import com.zjut.study.common.convention.code.ResultCodeEnum;

import java.io.Serializable;

/**
 * 默认的结果
 * @author jack
 */
public class DefaultResult<T> implements Result<T>, Serializable {
    private static final long serialVersionUID = -8869696654132628649L;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public DefaultResult<T> setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public DefaultResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public DefaultResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean isSuccess() {
        return ResultCodeEnum.SUCCESS.code().equals(code);
    }

    @Override
    public boolean isFailure() {
        return !this.isSuccess();
    }
}
