package com.emu.apps.qcm.domain.mappers;

import com.emu.apps.qcm.domain.model.tag.TagId;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagIdMapper {

    default TagId toTagId(UUID uuid) {
        return new TagId(uuid.toString());
    }

    default TagId toTagId(String uuid) {
        return new TagId(uuid);
    }

    default String toUuid(TagId id) {
        return id.toUuid();
    }

}
