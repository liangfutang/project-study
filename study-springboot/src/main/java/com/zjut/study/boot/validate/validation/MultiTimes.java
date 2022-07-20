package com.zjut.study.boot.validate.validation;

import com.zjut.study.boot.validate.validation.validator.MultiTimesValidatorForInteger;
import com.zjut.study.boot.validate.validation.validator.MultiTimesValidatorForList;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验传入的数是否是指定的倍数
 * @author jack
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {MultiTimesValidatorForInteger.class, MultiTimesValidatorForList.class}
)
public @interface MultiTimes {

    String message() default "{javax.validation.constraints.Size.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 是否是当前数的倍数
     * @return 倍数
     */
    int multiple() default 1;
}
