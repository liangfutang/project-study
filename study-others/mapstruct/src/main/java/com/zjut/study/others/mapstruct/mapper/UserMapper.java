package com.zjut.study.others.mapstruct.mapper;

import com.zjut.study.others.mapstruct.dto.UserDTO;
import com.zjut.study.others.mapstruct.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * 对象转换
 * @author jack
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * 直接简单的转换， DTO和VO中属性一样
     * @param userDTO
     * @return
     */
    UserVO convertDirect(UserDTO userDTO);

}
