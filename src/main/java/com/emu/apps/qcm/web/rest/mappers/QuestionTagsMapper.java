package com.emu.apps.qcm.web.rest.mappers;

import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.web.rest.dtos.question.QuestionTagsDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, QuestionTagMapper.class})
public abstract class QuestionTagsMapper {


    public  abstract QuestionTagsDto modelToDTo(Question question);

    public Page<QuestionTagsDto> pageQuestionResponseProjectionToDto(Page<Question> page) {
        return page.map(this::modelToDTo);
    }


//    @Named("responseWithOutResponse")
//    @Mapping(target = "response", ignore = true)
//    public  abstract ResponseDto modelToDtoWithoutStringResponse(Response response);
//
//    @Named("questionWithOutResponse")
//    @Mapping(target = "responses", qualifiedByName = "responseWithOutResponse")
//    public  abstract QuestionDto modelToDtoWithoutStringResponse(QuestionResponseProjection question);




}