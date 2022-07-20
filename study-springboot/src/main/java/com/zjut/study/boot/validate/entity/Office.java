package com.zjut.study.boot.validate.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author jack
 */
@Data
public class Office {

    private Integer id;

    @NotBlank(message = "办公室编号不能为空")
    private String code;

    @NotBlank(message = "办公室地址不能为空")
    private String address;
}
