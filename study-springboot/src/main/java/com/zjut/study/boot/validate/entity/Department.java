package com.zjut.study.boot.validate.entity;

import com.zjut.study.boot.validate.group.DeptAddGroup;
import com.zjut.study.boot.validate.group.EmpAddGroup;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * @author 部门
 */
@Data
public class Department {

    /**
     * 主键
     */
    @Null(groups = {EmpAddGroup.class, DeptAddGroup.class}, message = "id需要为空")
    private Integer id;

    /**
     * 部门名称
     */
    @NotBlank(groups = {EmpAddGroup.class, DeptAddGroup.class}, message = "部门名称不能为空")
    private String deptName;

    /**
     * 部门代码
     */
    @NotBlank(groups = {EmpAddGroup.class, DeptAddGroup.class}, message = "部门代码不能为空")
    private String deptCode;

    /**
     * 部门领导
     */
    @NotBlank(message = "部门领导不能为空")
    private String deptLead;

    /**
     * 部门员工
     */
    @Valid
    @NotEmpty(groups = {DeptAddGroup.class}, message = "部门员工不能为空")
    private List<Employee> employeeList;
}
