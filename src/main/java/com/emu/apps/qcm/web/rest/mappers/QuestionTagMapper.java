package com.emu.apps.qcm.web.rest.mappers;

import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.web.rest.dtos.QuestionTagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {EpicMapper.class})
public interface QuestionTagMapper {

    @Mappings({
            @Mapping(source = "tag.id", target = "tagId"),
            @Mapping(source = "tag.libelle", target = "libelle"),
            @Mapping(source = "tag.publique", target = "publique"),
            @Mapping(source = "question.id", target = "questionId")
    })
    QuestionTagDto modelToDto(QuestionTag questionTag);

    Iterable<QuestionTagDto> modelToDtos(Iterable<QuestionTag> questionTags);

//    @Mappings({
//            @Mapping(target = "tag.id", source = "tagId"),
//            @Mapping(target = "tag.libelle", source = "libelle"),
//            @Mapping(target = "tag.publique", source = "publique")
//    })
//   QuestionTag dtoToModel(QuestionTagDto questionTagDto);

}