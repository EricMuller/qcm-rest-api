package com.emu.apps.qcm.web.mappers;


import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.web.dtos.QuestionnaireDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;


@Mapper(componentModel = "spring", uses = {CategoryMapper.class, QuestionnaireTagMapper.class}
        , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionnaireMapper {

    QuestionnaireDto modelToDto(Questionnaire questionnaire);

    @Mapping(target = "questionnaireQuestions", ignore = true)
    @Mapping(target = "questionnaireTags", ignore = true)
    Questionnaire dtoToModel(QuestionnaireDto questionnaireDto);

    @Mapping(target = "questionnaireQuestions", ignore = true)
    @Mapping(target = "questionnaireTags", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    Questionnaire dtoToModel(@MappingTarget Questionnaire questionnaire, QuestionnaireDto questionnaireDto);

    default Page<QuestionnaireDto> pageToDto(Page<Questionnaire> page) {
        return page.map(this::modelToDto);
    }

}
