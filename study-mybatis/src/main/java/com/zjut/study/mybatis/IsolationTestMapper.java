package com.zjut.study.mybatis;

import com.zjut.study.mybatis.entity.IsolationTestDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author Jack
 */
//@Mapper
public interface IsolationTestMapper {

//    @Select({"select from isolation_test where id = #{id, jdbcType=LONG} "})
    List<IsolationTestDO> selectById(Integer id);
}
