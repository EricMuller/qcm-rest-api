package com.emu.apps.qcm.web.rest.mappers;

import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.web.rest.dtos.QuestionTagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class}
        , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionTagMapper {


    @Mapping(source = "tag.id", target = "id")
    @Mapping(source = "tag.libelle", target = "libelle")
    QuestionTagDto dtoToModel(QuestionTag questionTag);


    @Mapping(source = "id", target = "id.tagId")
    @Mapping(source = "libelle", target = "tag.libelle")
    QuestionTag dtoToModel(QuestionTagDto questionTagDto);

    Iterable<QuestionTag> dtosToModels(Iterable<QuestionTagDto> questionTagDtos);

}
