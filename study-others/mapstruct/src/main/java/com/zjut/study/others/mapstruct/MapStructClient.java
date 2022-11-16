package com.zjut.study.others.mapstruct;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.others.mapstruct.dto.UserDTO;
import com.zjut.study.others.mapstruct.mapper.UserMapper;
import com.zjut.study.others.mapstruct.vo.UserVO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 从DTO转到VO
 * @author jack
 */
public class MapStructClient extends CommonJunitFilter {

    /**
     * 对象到对象的 直接转换
     */
    @Test
    public void convert01() {
        UserDTO dto = UserDTO.builder().id(1).name("jack").age(12).build();

        UserVO vo = UserMapper.INSTANCE.convertDirect(dto);
        System.out.println("属性一样时转换结果: " + vo);
    }

    /**
     * 对象到对象 带参数类型 的转换
     */
    @Test
    public void convertFiledType01() {
        UserDTO dto = UserDTO.builder().id(1).name("jack").age(12).moneyDoub(12.3).build();

        UserVO vo = UserMapper.INSTANCE.convertDirectFiledType(dto);
        System.out.println("属性一样时转换结果: " + vo);
    }

    /**
     * 数组到数组的转换 带字段类型的转换
     */
    @Test
    public void convertList01() {
        List<UserDTO> dtoList = new ArrayList<>();
        dtoList.add(UserDTO.builder().id(1).name("jack").age(12).moneyDoub(1.2).build());
        dtoList.add(UserDTO.builder().id(1).name("jack").age(12).moneyDoub(2.3).build());
        dtoList.add(UserDTO.builder().id(1).name("jack").age(12).moneyDoub(3.4).build());

        List<UserVO> voList = UserMapper.INSTANCE.convertDirectList(dtoList);
        System.out.println("属性一样时转换结果: " + voList);
    }
}
