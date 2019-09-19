package com.emu.apps.qcm.web.mappers;

import com.emu.apps.qcm.services.jpa.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.web.dtos.QuestionnaireTagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class}
        , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionnaireTagMapper {


    @Mapping(source = "tag.id", target = "id")
    @Mapping(source = "tag.libelle", target = "libelle")
    QuestionnaireTagDto dtoToModel(QuestionnaireTag questionnaireTag);


    @Mapping(source = "id", target = "id.tagId")
    @Mapping(source = "libelle", target = "tag.libelle")
    QuestionnaireTag dtoToModel(QuestionnaireTagDto questionnaireTagDto);

    Iterable<QuestionnaireTag> dtosToModels(Iterable<QuestionnaireTagDto> questionnaireTagDtos);

}
