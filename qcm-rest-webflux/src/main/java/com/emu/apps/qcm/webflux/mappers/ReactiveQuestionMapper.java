package com.emu.apps.qcm.webflux.mappers;

import com.emu.apps.qcm.web.dtos.QuestionDto;
import com.emu.apps.qcm.webflux.model.questions.Question;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReactiveQuestionMapper {

    QuestionDto modelToDto(Question question);


}
