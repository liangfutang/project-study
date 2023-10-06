package com.zjut.study.common.convention.result;

/**
 * 提供对外的数据结果封装
 * @author tlf
 */
public interface Result<T>{

    /**
     * 获取错误码
     * @return 错误码
     */
    Integer getCode();

    /**
     * 获取成功或错误的信息
     * @return 成功或错误的信息
     */
    String getMessage();

    /**
     * 获取数据
     * @return 数据
     */
    T getData();

    /**
     * 设置错误码
     * @param code 错误码
     * @return Result对象
     */
    Result<T> setCode(Integer code);

    /**
     * 设置成功或错误的信息
     * @param message 成功或错误的信息
     * @return Result
     */
    Result<T> setMessage(String message);

    /**
     * 设置数据
     * @param data 数据
     * @return Result
     */
    Result<T> setData(T data);

    /**
     * 是否成功
     * @return boolean
     */
    boolean isSuccess();

    /**
     * 是否是业务处理失败，业务异常
     * @return boolean
     */
    boolean isFailure();

}
