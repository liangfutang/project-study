package com.zjut.study.boot.validate.entity;

import com.zjut.study.boot.validate.group.DeptAddGroup;
import com.zjut.study.boot.validate.group.EmpAddGroup;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author jack
 */
@Data
public class Employee {

    @Null(groups = {EmpAddGroup.class, DeptAddGroup.class}, message = "id需要为空")
    private Integer id;

    @NotBlank(groups = {EmpAddGroup.class, DeptAddGroup.class}, message = "姓名不能为空")
    private String name;

    @NotNull(groups = {EmpAddGroup.class, DeptAddGroup.class}, message = "年龄不能为空")
    private Integer age;

    @NotNull(message = "性别不能为空")
    private Integer sex;

    @Valid
    @NotNull(groups = {EmpAddGroup.class, DeptAddGroup.class}, message = "部门不能为空")
    private Department department;
}
