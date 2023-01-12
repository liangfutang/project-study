package com.zjut.study.boot.validate.enums;

import com.zjut.study.boot.validate.validation.valueable.IntArrayValuable;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SexEnum implements IntArrayValuable {

    MALE(1, "男子"),
    FEMALE(0, "女子");
    private Integer code;
    private String name;

    SexEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final int[] ARRAY = Arrays.stream(SexEnum.values()).mapToInt(SexEnum::getCode).toArray();
    @Override
    public int[] array() {
        return ARRAY;
    }
}
