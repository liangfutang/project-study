package com.zjut.study.common.convention.result;

/**
 * 提供对外的数据分页结果封装
 * @author tlf
 */
public interface PageResult<T> extends Result<T> {
    /**
     * 获取总数
     * @return 总数
     */
    Integer getTotal();

    /**
     * 设置总数
     * @param total 总数
     * @return Result
     */
    DefaultPageResult<T> setTotal(Integer total);
}
