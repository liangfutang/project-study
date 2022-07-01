package com.zjut.study.common.convention.code;


import com.zjut.study.common.exception.ServiceException;

/**
 * 服务错误码接口
 * @author jack
 */
public interface ServiceCode extends ResultCode {
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
}
