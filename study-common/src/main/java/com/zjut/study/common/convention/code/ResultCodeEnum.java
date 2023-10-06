package com.zjut.study.common.convention.code;

/**
 * 全局通用错误码信息
 *
 * @author tlf
 */
public enum ResultCodeEnum implements ServiceCode {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 操作失败
     */
    FAILURE(500, "操作失败"),

    /**
     * 没登录
     */
    NOT_LOGIN(401, "用户未登录"),
    /**
     * 登录异常
     */
    LOGIN_ECECPTION(402, "登录异常"),
    /**
     * 没有操作的权限
     */
    NO_AUTH(450, "没有权限的请求"),

    /**
     * 请求参数不合法
     */
    PARAMETER_ILLEGAL(2000, "请求参数不合法"),

    /**
     * 缺少必要的参数
     */
    NO_INPUT_PARAMETER_EXCEPTION(2002, "缺少必要的参数"),

    /**
     * 服务内部错误
     */
    INTERNAL_SERVER_ERROR(5000, "服务内部错误"),

    /**
     * 系统异常
     */
    SYS_FAILURE(1000, "未知系统异常"),

    /**
     * 业务异常
     */
    COMMON_FAILURE(3000, "未知操作异常"),

    /**
     * 读取redis异常
     */
    REDIS_READ_ERROR(4000, "读取redis异常"),

    /**
     * 写redis异常
     */
    REDIS_WRITE_ERROR(4001, "写redis异常");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
