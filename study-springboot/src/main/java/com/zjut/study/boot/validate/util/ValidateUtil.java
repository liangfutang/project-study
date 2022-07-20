package com.zjut.study.boot.validate.util;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.Validator;

/**
 * 获取到对应的校验器
 * @author jack
 */
@Component
public class ValidateUtil {

    public static Validator validator;

    @Resource
    public void setValidator(Validator validator) {
        ValidateUtil.validator = validator;
    }
}
