package com.zjut.study.common.exception;

import com.zjut.study.common.convention.code.ResultCodeEnum;
import com.zjut.study.common.convention.code.ServiceCode;

/**
 * 权限异常
 * @author tlf
 */
public class AuthException extends RuntimeException {

    private Integer code = ResultCodeEnum.NO_AUTH.getCode();

    public AuthException() {
        super(ResultCodeEnum.NO_AUTH.getMessage());
    }

    public AuthException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public AuthException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
    public AuthException(ServiceCode serviceCode) {
        super(serviceCode.getMessage());
        this.code = serviceCode.getCode();
    }
    public AuthException(String message) {
        super(message);
    }
    public AuthException(String message, Throwable throwable) {
        super(message, throwable);
    }
    public AuthException(Throwable throwable) {
        super(throwable);
    }

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
}
