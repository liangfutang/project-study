package com.zjut.study.common.convention.result;

import com.zjut.study.common.convention.code.ResultCodeEnum;
import com.zjut.study.common.convention.code.ServiceCode;
import com.zjut.study.common.exception.ServiceException;

/**
 * Result工具类，用于返回Result对象
 * @author jack
 */
public final class Results {

    /**
     * 成功
     * @return Result<Void>
     */
    public static Result<Void> success() {
        return new DefaultResult<Void>()
                .setCode(ResultCodeEnum.SUCCESS.code())
                .setMessage(ResultCodeEnum.SUCCESS.message());
    }

    /**
     * 成功
     * @param data 并设置data参数
     * @param <T> data的泛型
     * @return Result<T>
     */
    public static <T> Result<T> success(T data) {
        return new DefaultResult<T>()
                .setCode(ResultCodeEnum.SUCCESS.code())
                .setMessage(ResultCodeEnum.SUCCESS.message()).setData(data);
    }

    public static <T> Result<T> invalid() {
        return new DefaultResult<T>()
                .setCode(ResultCodeEnum.PARAMETER_ILLEGAL.code())
                .setMessage(ResultCodeEnum.PARAMETER_ILLEGAL.message());
    }

    public static <T> Result<T> invalid(String message) {
        return new DefaultResult<T>().setCode(ResultCodeEnum.PARAMETER_ILLEGAL.code()).setMessage(message);
    }

    /**
     * 服务异常，即业务逻辑异常
     * 是一种分支条件，或一种不能处理的状态，比如余额不足支付失败导致的异常对应的错误编号
     * @param serviceCode 异常code枚举
     * @param <T> 对应data字段的数据类型
     * @return Result<T>
     */
    public static <T> Result<T> failure(ServiceCode serviceCode) {
        return new DefaultResult<T>().setCode(serviceCode.code())
                .setMessage(serviceCode.message());
    }

    public static <T> Result<T> failure(String code, String message) {
        return new DefaultResult<T>().setCode(code)
                .setMessage(message);
    }

    public static <T> Result<T> failure(ServiceException serviceException) {
        return new DefaultResult<T>().setCode(serviceException.getCode())
                .setMessage(serviceException.getMessage());
    }

    /**
     * 返回带异常信息的响应结果，当成系统错误（bug）来处理
     */
    public static <T> Result<T> error() {
        return new DefaultResult<T>()
                .setCode(ResultCodeEnum.INTERNAL_SERVER_ERROR.code())
                .setMessage(ResultCodeEnum.INTERNAL_SERVER_ERROR.message());
    }

    /**
     *
     * 返回带异常信息的响应结果，可以自己明确的系统错误
     *
     * @param code 错误编号
     * @param message 错误信息
     * @param <T> 对应data字段的数据类型
     * @return result 对象
     */
    public static <T> Result<T> error(String code, String message) {
        return new DefaultResult<T>()
                .setCode(code)
                .setMessage(message);
    }
}
