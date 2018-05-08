package com.emu.apps.qcm.web.rest.mappers;


import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.projections.QuestionnaireProjection;
import com.emu.apps.qcm.web.rest.dtos.QuestionnaireDto;
import com.emu.apps.qcm.web.rest.dtos.SuggestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;


@Mapper(componentModel = "spring", uses = {EpicMapper.class, QuestionnaireTagMapper.class})
public interface QuestionnaireMapper {

    QuestionnaireDto modelToDto(Questionnaire questionnaire);

    @Mappings({
            @Mapping(target = "questionnaireQuestions", ignore = true),
            @Mapping(target = "questionnaireTags", ignore = true)
    })
    Questionnaire dtoToModel(QuestionnaireDto questionnaireDto);
//
//    Iterable<QuestionnaireDto> modelsToDtos(Iterable<Questionnaire> questionnaires);
//    @Mappings ({
//        @Mapping(target = "questions", ignore = true),
//        @Mapping(target = "category", ignore = true)
//    })
//    void updateQuestionnaire( @MappingTarget Questionnaire questionnaire,QuestionnaireDto ownerDto);

    default Page<QuestionnaireDto> pageToDto(Page<Questionnaire> page) {
        return page.map(this::modelToDto);
    }

    @Mappings({
            @Mapping(source = "question.title", target = "libelle"),
            @Mapping(target = "fieldName", constant = "questionnaireId"),
    })
    SuggestDto modelToSuggestDto(QuestionnaireProjection question);

    Iterable<SuggestDto> modelsToSuggestDtos(Iterable<QuestionnaireProjection> questionnaireProjections);

}
