package com.emu.apps.qcm.webflux.mappers;

import com.emu.apps.qcm.web.dtos.CategoryDto;
import com.emu.apps.qcm.webflux.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReactiveCategoryMapper {

    CategoryDto modelToDto(Category category);

    Category dtoToModel(CategoryDto categoryDto);

    Iterable <CategoryDto> modelsToDtos(Iterable <Category> categories);

}
