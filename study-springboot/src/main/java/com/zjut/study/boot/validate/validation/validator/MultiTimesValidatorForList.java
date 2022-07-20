package com.zjut.study.boot.validate.validation.validator;

import com.zjut.study.boot.validate.validation.MultiTimes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * 校验传入的数是否是当前指定数的倍数
 * @author jack
 */
public class MultiTimesValidatorForList implements ConstraintValidator<MultiTimes, List<?>> {

    /**
     * 指定的倍数
     */
    private int multiple;

    @Override
    public void initialize(MultiTimes constraintAnnotation) {
        this.multiple = constraintAnnotation.multiple();
    }

    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        if (list == null) {
            return false;
        }
        return list.size() % multiple == 0;
    }

}
