package com.zjut.study.common.convention.code;

/**
 * 错误码
 * @author jack
 */
public enum ResultCodeEnum implements ServiceCode {
    SUCCESS("0", "操作成功"),
    FAILURE("1", "操作失败"),
    PARAMETER_ILLEGAL("2000", "请求参数不合法"),
    INTERNAL_SERVER_ERROR("5000", "服务内部错误"),

    // 系统异常
    SYS_FAILURE("1000", SYS, "未知系统异常"),

    // 业务异常
    COMMON_FAILURE("3000", COMMON, "未知操作异常");

    private String code;
    private String message;

    ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultCodeEnum(String code, String codeSign, String message) {
        this.code = code + "_" + codeSign;
        this.message = message;
    }

    @Override
    public String message() {
        return this.message;
    }

    @Override
    public String code() {
        return this.code;
    }
}
