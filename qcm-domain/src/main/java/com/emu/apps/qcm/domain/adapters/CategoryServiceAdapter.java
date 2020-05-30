package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.domain.ports.CategoryService;
import com.emu.apps.qcm.infrastructure.ports.CategoryDOService;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.infrastructure.exceptions.FunctionnalException;
import com.emu.apps.qcm.web.dtos.CategoryDto;
import com.emu.apps.qcm.mappers.CategoryMapper;
import com.emu.apps.shared.security.PrincipalUtils;
import org.springframework.stereotype.Service;

import java.security.Principal;


/**
 *
 * Category Business Delegate
 *<p>
 *
 * @since 2.2.0
 * @author eric
 */
@Service
public class CategoryServiceAdapter implements CategoryService {

    private final CategoryDOService categoryDOService;

    private final CategoryMapper categoryMapper;

    public CategoryServiceAdapter(CategoryDOService categoryService, CategoryMapper categoryMapper) {
        this.categoryDOService = categoryService;
        this.categoryMapper = categoryMapper;
    }


    @Override
    public CategoryDto getCategory(Long id) {
        return categoryMapper.modelToDto(categoryDOService.findById(id).orElse(null));
    }


    @Override
    public Iterable <CategoryDto> getCategories(Principal principal, Type type) throws FunctionnalException {


        return categoryMapper.modelsToDtos(categoryDOService.findCategories(PrincipalUtils.getEmail(principal), type));
    }


    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto, Principal principal) throws FunctionnalException {
        var category = categoryMapper.dtoToModel(categoryDto);
        category.setUserId(PrincipalUtils.getEmail(principal));
        return categoryMapper.modelToDto(categoryDOService.saveCategory(category));
    }


}
