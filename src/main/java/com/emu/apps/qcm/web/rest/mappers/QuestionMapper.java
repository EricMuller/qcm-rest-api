package com.emu.apps.qcm.web.rest.mappers;

import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.projections.QuestionResponseProjection;
import com.emu.apps.qcm.web.rest.dtos.QuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {EpicMapper.class, QuestionTagMapper.class, ResponseMapper.class})
public abstract class QuestionMapper {

    @Mapping(target = "questionTags", ignore = true)
    public abstract Question dtoToModel(QuestionDto questionDto);

    public abstract QuestionDto modelToDto(Question question);

    public abstract QuestionDto questionResponseProjectionToDto(QuestionResponseProjection questionProjection);

    public Page<QuestionDto> pageQuestionResponseProjectionToDto(Page<QuestionResponseProjection> page) {
        return page.map(this::questionResponseProjectionToDto);
    }

}