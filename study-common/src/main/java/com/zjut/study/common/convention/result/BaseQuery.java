package com.zjut.study.common.convention.result;

import lombok.Data;

/**
 * @author tlf
 */
@Data
public abstract class BaseQuery extends PageParam {

    public BaseQuery() {}

    public BaseQuery(Integer pageNo, Integer pageSize) {
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    /**
     * 查询开始时间
     */
    private String startTime;

    /**
     * 查询截至时间
     */
    private String endTime;
}
