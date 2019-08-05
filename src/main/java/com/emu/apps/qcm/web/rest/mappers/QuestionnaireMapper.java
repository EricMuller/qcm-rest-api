package com.emu.apps.qcm.web.rest.mappers;


import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.jpa.projections.QuestionnaireProjection;
import com.emu.apps.qcm.web.rest.dtos.QuestionnaireDto;
import com.emu.apps.qcm.web.rest.dtos.SuggestDto;
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


    @Mapping(source = "question.title", target = "libelle")
    @Mapping(target = "fieldName", constant = "questionnaireId")
    SuggestDto modelToSuggestDto(QuestionnaireProjection question);

    Iterable<SuggestDto> modelsToSuggestDtos(Iterable<QuestionnaireProjection> questionnaireProjections);

}
