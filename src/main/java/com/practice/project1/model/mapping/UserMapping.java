package com.practice.project1.model.mapping;

import com.practice.project1.model.dto.UserDto;
import com.practice.project1.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapping {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
