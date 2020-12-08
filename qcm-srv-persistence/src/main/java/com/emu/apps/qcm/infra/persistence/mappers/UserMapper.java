package com.emu.apps.qcm.infra.persistence.mappers;


import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.UserEntity;
import com.emu.apps.qcm.domain.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {UuidMapper.class}
        , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User modelToDto(UserEntity user);

    UserEntity dtoToModel(User userDto);

    UserEntity dtoToModel(@MappingTarget UserEntity user, User userDto);

}
