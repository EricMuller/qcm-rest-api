package com.emu.apps.qcm.web.rest.mappers;

import com.emu.apps.qcm.services.jpa.entity.questions.Response;
import com.emu.apps.qcm.web.rest.dtos.ResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

    ResponseDto modelToDto(Response response);

}
