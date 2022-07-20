package com.zjut.study.boot.validate.entity;

import com.zjut.study.boot.validate.validation.MultiTimes;
import lombok.Data;

import java.util.List;

/**
 *
 * @author jack
 */
@Data
public class Job {

    private Integer id;

    private String name;

    /**
     * 该岗位的工人数量
     */
    @MultiTimes(multiple = 2, message = "当前数量不是指定数量的倍数")
    private Integer workerNum;

    /**
     * 岗位的标签
     */
    @MultiTimes(multiple = 2, message = "当前标签数量不是指定数量的倍数")
    private List<String> labes;
}
