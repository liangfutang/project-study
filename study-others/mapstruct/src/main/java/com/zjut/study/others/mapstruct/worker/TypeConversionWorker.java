package com.zjut.study.others.mapstruct.worker;

import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TypeConversionWorker {

    @Named("double2long")
    public Long double2long(Double origin) {
        if (origin == null) {
            return null;
        }
        return new BigDecimal(origin).setScale(1, RoundingMode.HALF_UP).longValue();
    }
}
