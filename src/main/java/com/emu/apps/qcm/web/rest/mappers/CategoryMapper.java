package com.emu.apps.qcm.web.rest.mappers;

import com.emu.apps.qcm.services.jpa.entity.category.Category;
import com.emu.apps.qcm.web.rest.dtos.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring"
        ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryDto modelToDto(Category category);

    Category dtoToModel(CategoryDto categoryDto);

    Iterable<CategoryDto> modelsToDtos(Iterable<Category> categories);
}
