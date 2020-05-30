package com.emu.apps.qcm.mappers;


import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.users.User;
import com.emu.apps.qcm.web.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto modelToDto(User user);

    User dtoToModel(UserDto userDto);

    User dtoToModel(@MappingTarget User user, UserDto userDto);

}
