package com.emu.apps.qcm.services;

import com.emu.apps.qcm.domain.CategoryDOService;
import com.emu.apps.qcm.domain.entity.category.Type;
import com.emu.apps.qcm.domain.exceptions.FunctionnalException;
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
public class CategoryService {

    private final CategoryDOService categoryDOService;

    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryDOService categoryService, CategoryMapper categoryMapper) {
        this.categoryDOService = categoryService;
        this.categoryMapper = categoryMapper;
    }


    public CategoryDto getCategory(Long id) {
        return categoryMapper.modelToDto(categoryDOService.findById(id).orElse(null));
    }


    public Iterable <CategoryDto> getCategories(Principal principal, Type type) throws FunctionnalException {


        return categoryMapper.modelsToDtos(categoryDOService.findCategories(PrincipalUtils.getEmail(principal), type));
    }


    public CategoryDto saveCategory(CategoryDto categoryDto, Principal principal) throws FunctionnalException {
        var category = categoryMapper.dtoToModel(categoryDto);
        category.setUserId(PrincipalUtils.getEmail(principal));
        return categoryMapper.modelToDto(categoryDOService.saveCategory(category));
    }


}
