package com.zjut.study.boot.validate.servicevalid;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.boot.validate.entity.Employee;
import com.zjut.study.boot.validate.group.EmpAddGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 不实现接口，对方法中参数校验
 * @author jack
 */
@Service
@Slf4j
@Validated(EmpAddGroup.class)
public class EmployeeServiceImpl {

    public void add(@Valid Employee employee) {
        log.info("不实现接口方法层参数校验:{}", JSONObject.toJSONString(employee));
    }
}
