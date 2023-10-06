package com.zjut.study.common.exception;

import com.zjut.study.common.convention.code.ResultCodeEnum;

/**
 * 参数异常
 * @author jack
 */
public class ParameterException extends RuntimeException {
    private Integer code;

    public ParameterException() {
        super(ResultCodeEnum.PARAMETER_ILLEGAL.message());
        this.code = ResultCodeEnum.PARAMETER_ILLEGAL.code();
    }

    public ParameterException(Integer code, String message) {
        super(message);
        this.code = ResultCodeEnum.PARAMETER_ILLEGAL.code();
        this.code = code;
    }

    public ParameterException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.message());
        this.code = ResultCodeEnum.PARAMETER_ILLEGAL.code();
        this.code = resultCodeEnum.code();
    }

    public ParameterException(String message) {
        super(message);
        this.code = ResultCodeEnum.PARAMETER_ILLEGAL.code();
    }

    public ParameterException(String message, Throwable throwable) {
        super(message, throwable);
        this.code = ResultCodeEnum.PARAMETER_ILLEGAL.code();
    }

    public ParameterException(Throwable throwable) {
        super(throwable);
        this.code = ResultCodeEnum.PARAMETER_ILLEGAL.code();
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
