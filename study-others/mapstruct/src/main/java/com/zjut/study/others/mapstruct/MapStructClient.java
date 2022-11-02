package com.zjut.study.others.mapstruct;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.others.mapstruct.dto.UserDTO;
import com.zjut.study.others.mapstruct.mapper.UserMapper;
import com.zjut.study.others.mapstruct.vo.UserVO;
import org.junit.Test;

/**
 * 从DTO转到VO
 * @author jack
 */
public class MapStructClient extends CommonJunitFilter {

    @Test
    public void convert01() {
        UserDTO dto = UserDTO.builder().id(1).name("jack").age(12).build();

        UserVO vo = UserMapper.INSTANCE.convertDirect(dto);
        System.out.println("属性一样时转换结果: " + vo);
    }
}
