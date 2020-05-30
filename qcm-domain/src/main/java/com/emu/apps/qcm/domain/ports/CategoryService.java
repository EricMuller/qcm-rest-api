package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.infrastructure.exceptions.FunctionnalException;
import com.emu.apps.qcm.web.dtos.CategoryDto;

import java.security.Principal;

public interface CategoryService {
    CategoryDto getCategory(Long id);

    Iterable <CategoryDto> getCategories(Principal principal, Type type) throws FunctionnalException;

    CategoryDto saveCategory(CategoryDto categoryDto, Principal principal) throws FunctionnalException;
}
