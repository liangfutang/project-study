package com.zjut.study.mybatis.mj.mapper;

import com.zjut.study.mybatis.mj.entity.IsolationTestDO;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author Jack
 */
//@Mapper
//    @CacheNamespace
public interface IsolationTestMapper {

//    @Select({"select from isolation_test where id = #{id, jdbcType=LONG} "})
    Object selectById(Integer id);

    @Select({"select id, name, age from isolation_test where name = #{name} "})
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "audit", property = "audit"),
            @Result(column = "age", property = "age")
    })
    List<IsolationTestDO> selectById1(String name);
}
