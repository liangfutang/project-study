package com.zjut.study.others.mapstruct.mapper;

import com.zjut.study.others.mapstruct.dto.UserDTO;
import com.zjut.study.others.mapstruct.vo.UserVO;
import com.zjut.study.others.mapstruct.worker.TypeConversionWorker;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 对象转换
 * @author jack
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {TypeConversionWorker.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * 直接简单的转换， DTO和VO中属性一样
     * @param userDTO
     * @return
     */
    @Named("one")
    UserVO convertDirect(UserDTO userDTO);

    /**
     * 不同类型字段之间做转换
     * @param userDTO
     * @return
     */
    @Named("two")
    @Mapping(source = "moneyDoub", target = "money", qualifiedByName = "double2long")
    UserVO convertDirectFiledType(UserDTO userDTO);

    @IterableMapping(qualifiedByName = "two")
    List<UserVO> convertDirectList(List<UserDTO> dtos);

}
