package com.zjut.study.others.mapstruct.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author jack
 */
@Data
@Builder
@AllArgsConstructor
public class UserVO {

    private Integer id;

    private String name;

    private Integer age;

    private Long money;
}
