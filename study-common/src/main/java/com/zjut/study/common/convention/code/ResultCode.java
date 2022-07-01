package com.zjut.study.common.convention.code;

/**
 * 相应码接口类
 * @author jack
 */
public interface ResultCode {

    /**
     * 异常响应码标识
     */
    String SYS = "SYS";

    /**
     * 通用响应码标识
     */
    String COMMON = "C";

    /**
     * 通用响应码标识
     */
    String AUTH = "AUTH";

    /**
     * 获取相应码
     */
    String code();

    /**
     * 获取响应码信息
     */
    String message();
}
