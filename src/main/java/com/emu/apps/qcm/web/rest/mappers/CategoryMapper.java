package com.emu.apps.qcm.web.rest.mappers;

import com.emu.apps.qcm.services.entity.category.Category;
import com.emu.apps.qcm.web.rest.dtos.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto modelToDto(Category category);

    Category dtoToModel(CategoryDto categoryDto);

    Iterable<CategoryDto> modelsToDtos(Iterable<Category> categories);
}