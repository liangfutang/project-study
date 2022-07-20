package com.zjut.study.boot.validate.servicevalid.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.boot.validate.entity.Department;
import com.zjut.study.boot.validate.group.DeptAddGroup;
import com.zjut.study.boot.validate.servicevalid.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 实现接口的方法参数校验
 * @author jack
 */
@Service
@Slf4j
@Validated // 此处校验注解不能少，否则会失效
public class DepartmentServiceImpl implements DepartmentService {

    @Override
    public void add(@Validated(DeptAddGroup.class)Department department) {
        log.info("实现接口方法层接口校验:{}", JSONObject.toJSONString(department));
    }

    @Override
    public @NotNull(message = "不能返回空部门") Department getById(Integer id) {
        return Objects.isNull(id) ? null : new Department();
    }
}
