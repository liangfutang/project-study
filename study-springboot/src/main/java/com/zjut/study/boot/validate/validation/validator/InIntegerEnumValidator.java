package com.zjut.study.boot.validate.validation.validator;

import cn.hutool.core.util.ArrayUtil;
import com.zjut.study.boot.validate.validation.InIntegerEnum;
import com.zjut.study.boot.validate.validation.valueable.IntArrayValuable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对入参值进行校验是否在指定的数组范围内
 * @author jack
 */
public class InIntegerEnumValidator implements ConstraintValidator<InIntegerEnum, Integer> {

    private List<Integer> values;

    @Override
    public void initialize(InIntegerEnum constraintAnnotation) {
        IntArrayValuable[] constraintValue = constraintAnnotation.value().getEnumConstants();
        if (ArrayUtil.isEmpty(constraintValue)) {
            values = new ArrayList<>();
        } else {
            values = Arrays.stream(constraintValue[0].array()).boxed().collect(Collectors.toList());
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // 如果传入的value为空，则校验不通过
        if (value==null) {
            return false;
        }
        // 如果传入的值在指定的值的范围内，则通过
        if (values.contains(value)) {
            return true;
        }
        // 校验不通过，自定义提示语句（因为，注解上的 value 是枚举类，无法获得枚举类的实际值）
        context.disableDefaultConstraintViolation(); // 禁用默认的 message 的值
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
                .replaceAll("\\{value}", values.toString())).addConstraintViolation(); // 重新添加错误提示语句
        return false;
    }
}
