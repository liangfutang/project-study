package com.zjut.study.common.exception;

import com.zjut.study.common.convention.code.ServiceCode;

/**
 * redis相关异常
 * @author tlf
 */
public class RedisOperationException extends RuntimeException {
    private static final long serialVersionUID = -5102560559568169695L;

    protected Integer code;

    public RedisOperationException() {
        super();
    }

    public RedisOperationException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public RedisOperationException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public RedisOperationException(ServiceCode respCode) {
        super(respCode.message());
        this.code = respCode.code();
    }

    public RedisOperationException(ServiceCode respCode, Throwable cause) {
        super(respCode.message(), cause);
        this.code = respCode.code();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDetailMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "RedisOperationException{" +
                "code='" + code + '\'' +
                "message='" + getMessage() + '\'' +
                '}';
    }
}
