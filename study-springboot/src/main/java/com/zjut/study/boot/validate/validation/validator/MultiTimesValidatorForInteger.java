package com.zjut.study.boot.validate.validation.validator;

import com.zjut.study.boot.validate.validation.MultiTimes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * 校验传入的数是否是当前指定数的倍数
 * @author jack
 */
public class MultiTimesValidatorForInteger implements ConstraintValidator<MultiTimes, Integer> {

    /**
     * 指定的倍数
     */
    private int multiple;

    @Override
    public void initialize(MultiTimes constraintAnnotation) {
        this.multiple = constraintAnnotation.multiple();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return false;
        }
        return value % multiple == 0;
    }
}
