package com.zjut.study.auth.oauth2.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 授权的第三方信息
 * @author jack
 */
public enum ThirdPartyEnum {

    WEIXIN("weixin", "微信");

    @Getter
    private String code;
    private String name;

    ThirdPartyEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 校验code是否是当前所有第三方中的一种
     * @param code
     * @return true:正确的第三方，false:错误的第三方
     */
    public static boolean checkThirdPartyByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return false;
        }
        for (ThirdPartyEnum one : ThirdPartyEnum.values()) {
            if (Objects.equals(one.code, code)) {
                return true;
            }
        }
        return false;
    }

}
