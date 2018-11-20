package com.emu.apps.qcm.web.rest.mappers;


import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import com.emu.apps.qcm.web.rest.dtos.SuggestDto;
import com.emu.apps.qcm.web.rest.dtos.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto modelToDto(Tag tag);

    Tag dtoToModel(TagDto tagDto);

    Iterable <TagDto> modelsToDtos(Iterable <Tag> tags);

    default Page <TagDto> pageToDto(Page <Tag> page) {
        return page.map(this::modelToDto);
    }

    @Mappings({
            @Mapping(target = "fieldName", constant = "TagId"),
    })
    SuggestDto modelToSuggestDto(Tag tag);

    Iterable <SuggestDto> modelsToSugestDtos(Iterable <Tag> tags);
}