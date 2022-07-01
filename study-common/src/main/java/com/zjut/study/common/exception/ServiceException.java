package com.zjut.study.common.exception;

import com.zjut.study.common.convention.code.ServiceCode;

/**
 * 业务系统业务逻辑相关异常
 * @author jack
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -5102560559568169695L;

    protected String code;

    public ServiceException() {
        super();
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(ServiceCode respCode) {
        super(respCode.message());
        this.code = respCode.code();
    }

    public ServiceException(ServiceCode respCode, Throwable cause) {
        super(respCode.message(), cause);
        this.code = respCode.code();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetailMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "code='" + code + '\'' +
                "message='" + getMessage() + '\'' +
                '}';
    }
}
