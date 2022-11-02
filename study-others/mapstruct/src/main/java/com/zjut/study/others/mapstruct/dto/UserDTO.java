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
}
