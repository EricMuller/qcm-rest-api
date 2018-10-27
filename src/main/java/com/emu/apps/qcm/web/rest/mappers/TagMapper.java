package com.emu.apps.qcm.web.rest.mappers;


import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import com.emu.apps.qcm.web.rest.dtos.SuggestDto;
import com.emu.apps.qcm.web.rest.dtos.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public abstract class TagMapper {

    public abstract TagDto modelToDto(Tag tag);

    public abstract Tag dtoToModel(TagDto tagDto);

    public abstract Iterable<TagDto> modelsToDtos(Iterable<Tag> tags);

    public Page<TagDto> pageToDto(Page<Tag> page) {
        return page.map(this::modelToDto);
    }

    @Mappings({
            @Mapping(target = "fieldName", constant = "TagId"),
    })
    public abstract SuggestDto modelToSuggestDto(Tag tag);

    public abstract Iterable<SuggestDto> modelsToSugestDtos(Iterable<Tag> tags);
}