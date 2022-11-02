package com.zjut.study.others.mapstruct.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author jack
 */
@Data
@Builder
public class UserVO {

    private Integer id;

    private String name;

    private Integer age;
}
