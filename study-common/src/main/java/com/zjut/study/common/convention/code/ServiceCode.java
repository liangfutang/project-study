package com.zjut.study.common.convention.code;

import com.zjut.study.common.exception.ServiceException;

/**
 * 服务错误码接口
 * @author tlf
 */
public interface ServiceCode {

    /**
     * 提供给外部需要的错误码
     * @return 提供出去的错误码（工程代码+错误码）
     */
    default Integer code() {
        return getCode();
    }

    /**
     * 提供给外部需要的错误信息
     * @return 提供出去的错误信息
     */
    default String message() {
        return getMessage();
    }

    /**
     * 返回服务异常
     * @return Service Exception
     */
    default ServiceException failure() {
        return new ServiceException(this);
    }

    /**
     * 返回服务异常
     * @param cause 原始异常
     * @return Service Exception
     */
    default ServiceException failure(Throwable cause) {
        return new ServiceException(this, cause);
    }



    /**
     * 内部获取错误信息
     * @return 错误码描述
     */
    String getMessage();

    /**
     * 内部获取实现后自定义的错误码
     * @return 内部定义错误码中一部分
     */
    Integer getCode();

}
