package com.emu.apps.qcm.web.rest.mappers;

import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.web.rest.dtos.question.QuestionTagsDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, QuestionTagMapper.class})
public interface QuestionTagsMapper {


    QuestionTagsDto modelToDTo(Question question);

    default Page<QuestionTagsDto> pageQuestionResponseProjectionToDto(Page<Question> page) {
        return page.map(this::modelToDTo);
    }


}