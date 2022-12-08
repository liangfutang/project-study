package com.zjut.study.boot.vo;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author jack
 */
@Data
@Accessors(chain = true)
public class UserVO {
    private Integer id;
    @JsonView({UserSimpleView.class})
    private String name;
    private String nikeName;
    @JsonView({UserSimpleView.class})
    private Integer age;

    public interface UserSimpleView{}
}
