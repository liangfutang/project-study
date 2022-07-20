package com.zjut.study.boot.validate.validation;

import com.zjut.study.boot.validate.validation.validator.MultiTimesValidatorForInteger;
import com.zjut.study.boot.validate.validation.validator.MultiTimesValidatorForList;
import com.zjut.study.boot.validate.validation.validator.ValidListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 针对入参是List数组的校验
 * @author jack
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {ValidListValidator.class}
)
public @interface ValidList {

    String message() default "{javax.validation.constraints.Size.message}";

    Class<?>[] groups() default {};

    /**
     * 使用的事自定义的分组
     * @return
     */
    Class<?>[] groupings() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 对数组遍历校验时，如果其中一个校验参数异常是否快速失败
     * @return
     */
    boolean quickFail() default false;
}
