package com.zjut.study.mybatis.boot.mapper;

import com.zjut.study.mybatis.boot.entity.UserDO;


/**
 * @author Jack
 */
//@Mapper
public interface UserMapper {

    UserDO selectById(Integer id);

//    @Select({"select id, name, age from isolation_test where name = #{name} "})
//    @Results({
//            @Result(id = true, column = "id", property = "id"),
//            @Result(column = "name", property = "name"),
//            @Result(column = "audit", property = "audit"),
//            @Result(column = "age", property = "age")
//    })
}
