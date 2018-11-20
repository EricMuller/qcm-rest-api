package com.emu.apps.qcm.web.rest.mappers;

import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.services.jpa.projections.QuestionResponseProjection;
import com.emu.apps.qcm.web.rest.dtos.QuestionDto;
import com.emu.apps.qcm.web.rest.dtos.question.QuestionTagsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, QuestionTagMapper.class, ResponseMapper.class})
public interface QuestionMapper {

    @Mapping(target = "questionTags", ignore = true)
    Question dtoToModel(QuestionDto questionDto);

    @Mappings({
            @Mapping(target = "questionTags", ignore = true),
            @Mapping(target = "uuid", ignore = true),
            @Mapping(target = "dateCreation", ignore = true),
    })
    Question dtoToModel(@MappingTarget Question question, QuestionDto questionDto);

    QuestionDto modelToDto(Question question);

    QuestionTagsDto modelToPageTagDto(Question question);

    QuestionDto questionResponseProjectionToDto(QuestionResponseProjection questionProjection);

    default Page <QuestionDto> pageQuestionResponseProjectionToDto(Page <QuestionResponseProjection> page) {
        return page.map(this::questionResponseProjectionToDto);
    }

    default Page <QuestionTagsDto> pageToPageTagDto(Page <Question> page) {
        return page.map(this::modelToPageTagDto);
    }


}