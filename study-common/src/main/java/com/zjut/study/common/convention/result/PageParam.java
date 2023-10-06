package com.zjut.study.common.convention.result;

import com.zjut.study.common.exception.ParameterException;

/**
 * 基础分页参数
 * @author tlf
 */
public class PageParam {
    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    /**
     * 接受到的页码
     */
    private Integer pageNo = PAGE_NO;
    /**
     * 接受到的每页个数
     */
    private Integer pageSize = PAGE_SIZE;

    /**
     * 页码的自增
     */
    public void pageIncr() {
        this.pageNo += 1;
    }

    /**
     * 页码的自增
     * @param num 增加指定大小的页码
     */
    public void pageIncr(Integer num) {
        if (num==null || num<1) {
            throw new ParameterException("无效的增加页码数");
        }
        this.pageNo += num;
    }


    /**
     * sql 参数
     * @return sql 分页起点
     */
    public Integer getOffset() {
        return (pageNo-1) * pageSize;
    }
    /**
     * sql 参数
     * @return sql 查询数量
     */
    public Integer getCount() {
        return pageSize;
    }


    public Integer getPageNo() {
        return pageNo;
    }

    /**
     * @param pageNo 传入页码值
     */
    public void setPageNo(Integer pageNo) {
        // 如果传页码值必须从第一页开始
        if (pageNo != null && pageNo < 1) {
            throw new ParameterException("当前页码无效");
        }
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize 查询页码大小
     */
    public void setPageSize(Integer pageSize) {
        // 如果传页面值必须是有效值
        if (pageSize != null && pageSize<1) {
            throw new ParameterException("无效查询页面个数");
        }
        this.pageSize = pageSize;
    }
}
