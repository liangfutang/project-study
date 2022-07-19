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
     * 这里如果不加group，但是@Validated中有group，则校验会失效
     * @param age
     * @return
     */
    @NotNull(groups = {DeptAddGroup.class}) Integer updateAge(Integer age);
}
