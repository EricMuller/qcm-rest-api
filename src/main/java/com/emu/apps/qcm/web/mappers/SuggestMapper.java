package com.emu.apps.qcm.web.mappers;

import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import com.emu.apps.qcm.services.jpa.projections.QuestionnaireProjection;
import com.emu.apps.qcm.web.dtos.SuggestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SuggestMapper {


    @Mapping(target = "fieldName", constant = "TagId")
    SuggestDto modelToSuggestDto(Tag tag);

    Iterable<SuggestDto> modelsToSugestDtos(Iterable<Tag> tags);

    @Mapping(source = "question.title", target = "libelle")
    @Mapping(target = "fieldName", constant = "questionnaireId")
    SuggestDto modelToSuggestDto(QuestionnaireProjection question);

    Iterable<SuggestDto> modelsToSuggestDtos(Iterable<QuestionnaireProjection> questionnaireProjections);
}
