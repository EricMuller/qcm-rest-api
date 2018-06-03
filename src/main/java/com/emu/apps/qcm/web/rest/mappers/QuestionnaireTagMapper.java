package com.emu.apps.qcm.web.rest.mappers;

import com.emu.apps.qcm.services.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.web.rest.dtos.QuestionnaireTagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface QuestionnaireTagMapper {

    @Mappings({
            @Mapping(source = "tag.id", target = "id"),
            @Mapping(source = "tag.libelle", target = "libelle"),
    })
    QuestionnaireTagDto dtoToModel(QuestionnaireTag questionnaireTag);

    @Mappings({
            @Mapping(source = "id", target = "id.tagId"),
            @Mapping(source = "libelle", target = "tag.libelle"),
    })
    QuestionnaireTag dtoToModel(QuestionnaireTagDto questionnaireTagDto);

    Iterable<QuestionnaireTag> dtosToModels(Iterable<QuestionnaireTagDto> questionnaireTagDtos);

}