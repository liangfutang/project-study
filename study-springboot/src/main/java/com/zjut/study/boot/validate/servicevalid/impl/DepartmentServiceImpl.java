package com.zjut.study.boot.validate.servicevalid.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.boot.validate.entity.Department;
import com.zjut.study.boot.validate.group.DeptAddGroup;
import com.zjut.study.boot.validate.servicevalid.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Service
@Slf4j
@Validated(DeptAddGroup.class)
public class DepartmentServiceImpl implements DepartmentService {

    @Override
    public void add(Department department) {
        log.info("方法层接口校验:{}", JSONObject.toJSONString(department));
    }

    @Override
    public Integer updateAge(Integer age) {
        return Objects.isNull(age) ? null : 1;
    }
}
