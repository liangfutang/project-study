package com.zjut.study.boot.validate.validation.validator;

import com.zjut.study.boot.validate.util.ValidateUtil;
import com.zjut.study.boot.validate.validation.ValidList;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 针对入参是List数组的校验
 * @author jack
 */
public class ValidListValidator implements ConstraintValidator<ValidList, List<?>> {

    private Class<?>[] groupings;
    private boolean quickFail;

    @Override
    public void initialize(ValidList constraintAnnotation) {
        this.groupings = constraintAnnotation.groupings();
        this.quickFail = constraintAnnotation.quickFail();
    }

    @Override
    public boolean isValid(List<?> objects, ConstraintValidatorContext constraintValidatorContext) {
        // 跟自带的其他校验器保持类似，先对为null的做排除
        if (objects == null) {
            return false;
        }
        Set<ConstraintViolation<Object>> errors = new HashSet<>();
        for (Object o : objects) {
            Set<ConstraintViolation<Object>> validateResult = ValidateUtil.validator.validate(o, groupings);
            if (validateResult.size() > 0) {
                errors.addAll(validateResult);
            }
            // 快速失败
            if (quickFail) {
//                return false;
                throw new ConstraintViolationException(validateResult);
            }
        }
        if (CollectionUtils.isNotEmpty(errors)) {
            throw new ConstraintViolationException(errors);
        }
        return false;
    }
}
