package com.zjut.study.boot.validate.entity;

import com.zjut.study.boot.validate.enums.SexEnum;
import com.zjut.study.boot.validate.group.EmpAddGroup;
import com.zjut.study.boot.validate.validation.InIntegerEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jack
 */
@Data
public class Company {

    @NotNull(message = "性别不能为空")
    @InIntegerEnum(value = SexEnum.class, groups = {EmpAddGroup.class})
    private Integer sex;
}
