package com.zjut.study.others.mapstruct.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author jack
 */
@Data
@Builder
public class UserDTO {

    private Integer id;

    private String name;

    private Integer age;

    /**
     * 测试DTO中Double向VO中的Long转换
     */
    private Long money;
    private Double moneyDoub;
}
