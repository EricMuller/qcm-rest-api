package com.emu.apps.qcm.web.rest.mappers;

import com.emu.apps.qcm.services.entity.questions.Response;
import com.emu.apps.qcm.web.rest.dtos.ResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

    ResponseDto modelToDto(Response response);


//    @Named("responseWithOutResponse")
//    @Mapping(target = "response", ignore = true)
//    public  abstract ResponseDto modelToDtoWithoutStringResponse(Response response);
//
//    @Named("questionWithOutResponse")
//    @Mapping(target = "responses", qualifiedByName = "responseWithOutResponse")
//    public  abstract QuestionDto modelToDtoWithoutStringResponse(QuestionResponseProjection question);

}
