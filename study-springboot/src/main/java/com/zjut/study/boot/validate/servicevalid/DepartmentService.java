package com.zjut.study.boot.validate.servicevalid;

import com.zjut.study.boot.validate.entity.Department;
import com.zjut.study.boot.validate.group.DeptAddGroup;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface DepartmentService {

    /**
     * 方法层校验，且有interface接口的，需要将@Valid放在接口中
     * @param department
     * @return
     */
    void add(@Valid Department department);

    /**
     * 对返回值的是否为空的校验 @NotNull 可以加在这里也能加在方法实现上，有一处加了就可以
     *
     * @param id
     * @return
     */
    Department getById(Integer id);
}
