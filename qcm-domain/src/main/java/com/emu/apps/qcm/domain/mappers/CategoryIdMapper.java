package com.emu.apps.qcm.domain.mappers;

import com.emu.apps.qcm.domain.model.category.CategoryId;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryIdMapper {

    default CategoryId toCategoryId(UUID uuid) {
        return new CategoryId(uuid.toString());
    }

    default CategoryId toCategoryId(String uuid) {
        return isNotBlank(uuid) ? new CategoryId(uuid) : null;
    }

    default String categoryIdToUuid(CategoryId id) {
        return id.toUuid();
    }

}
