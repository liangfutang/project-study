package com.zjut.study.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.boot.validate.entity.Department;
import com.zjut.study.boot.validate.entity.Employee;
import com.zjut.study.boot.validate.entity.Job;
import com.zjut.study.boot.validate.group.DeptAddGroup;
import com.zjut.study.boot.validate.group.EmpAddGroup;
import com.zjut.study.boot.validate.servicevalid.DepartmentService;
import com.zjut.study.boot.validate.servicevalid.EmployeeServiceImpl;
import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.groups.Default;

/**
 * 参数校验
 *
 * @author jack
 */
@RestController
@Slf4j
@RequestMapping("/valid")
public class ValidationController {

    /**
     * 一对一的关系的级联参数校验
     * 需要对内部的嵌套对象添加 @Valid 说明，否则不会对其校验
     *
     * @param employee 员工
     * @return
     */
    @PostMapping("/one/2/one/cascade")
    public Result<?> one2one(@Validated(EmpAddGroup.class) @RequestBody Employee employee) {
        log.info("一对一关系下参数级联校验:{}", JSONObject.toJSONString(employee));
        return Results.success();
    }

    /**
     * 一对多的级联校验
     * 需要在多上添加 @Valid 说明
     * 分组的时候使用  Default.class  表示使用默认的分组，会对属性上没有添加任何分组(有分组则不是Default了)的也做校验
     *
     * @param department
     * @return
     */
    @PostMapping("/one/2/one/many")
    public Result<?> one2many(@Validated({DeptAddGroup.class, Default.class}) @RequestBody Department department) {
        log.info("一对多关系下参数级联校验:{}", JSONObject.toJSONString(department));
        return Results.success();
    }

    // ===========================方法层参数校验=============================

    @Resource
    private EmployeeServiceImpl mployeeServiceImpl;
    @Resource
    private DepartmentService departmentService;

    /**
     * 没实现接口的方法层校验
     * service层校验异常是ConstraintViolationException
     *
     * @param employee
     * @return
     */
    @PostMapping("/service/one/2/one/emp")
    public Result<?> serviceOne2one(@RequestBody Employee employee) {
        mployeeServiceImpl.add(employee);
        return Results.success();
    }

    /**
     * 有实现接口的方法层校验
     * @param department
     * @return
     */
    @PostMapping("/service/one/2/one/dept")
    public Result<?> serviceOne2many(@RequestBody Department department) {
        departmentService.add(department);
        return Results.success();
    }
    @PutMapping("/service/one/2/one/dept")
    public Result<?> getById(@RequestParam(required = false) Integer id) {
        departmentService.getById(id);
        return Results.success();
    }

    // =============================自定义校验=================================

    @PutMapping("/customize/validator/multi")
    public Result<?> updateAge(@Validated @RequestBody Job job) {
        log.info("自定义注解校验传入的参数是否是指定数的倍数:{}", job);
        return Results.success();
    }
}
