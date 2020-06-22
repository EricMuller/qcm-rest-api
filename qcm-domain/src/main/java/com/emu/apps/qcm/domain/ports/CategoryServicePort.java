package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.models.CategoryDto;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.infrastructure.exceptions.FunctionnalException;

public interface CategoryServicePort {

    CategoryDto getCategoryByUuid(String id);

    Iterable <CategoryDto> getCategories(String principal, Type type) throws FunctionnalException;

    CategoryDto saveCategory(CategoryDto categoryDto, String principal) throws FunctionnalException;

}
