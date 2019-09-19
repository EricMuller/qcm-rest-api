package com.emu.apps.qcm.web.mappers;


import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import com.emu.apps.qcm.web.dtos.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring"
        ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    TagDto modelToDto(Tag tag);

    Tag dtoToModel(TagDto tagDto);

    Iterable <TagDto> modelsToDtos(Iterable <Tag> tags);

    default Page <TagDto> pageToDto(Page <Tag> page) {
        return page.map(this::modelToDto);
    }


}
