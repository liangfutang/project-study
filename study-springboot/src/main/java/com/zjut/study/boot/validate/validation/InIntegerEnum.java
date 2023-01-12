package com.zjut.study.boot.validate.validation;

import com.zjut.study.boot.validate.validation.validator.InIntegerEnumValidator;
import com.zjut.study.boot.validate.validation.valueable.IntArrayValuable;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验传入的入参是否在指定的范围内
 * @author jack
 */
@Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = InIntegerEnumValidator.class
)
public @interface InIntegerEnum {

    Class<? extends IntArrayValuable> value();

    String message() default "必须在指定范围 {value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
