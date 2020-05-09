package com.emu.apps.qcm.business;

import com.emu.apps.qcm.services.CategoryService;
import com.emu.apps.qcm.services.entity.category.Type;
import com.emu.apps.qcm.services.exceptions.FunctionnalException;
import com.emu.apps.qcm.web.dtos.CategoryDto;
import com.emu.apps.qcm.web.mappers.CategoryMapper;
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
public class CategoryDelegate {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    public CategoryDelegate(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }


    public CategoryDto getCategory(Long id) {
        return categoryMapper.modelToDto(categoryService.findById(id).orElse(null));
    }


    public Iterable <CategoryDto> getCategories(Principal principal, Type type) throws FunctionnalException {


        return categoryMapper.modelsToDtos(categoryService.findCategories(PrincipalUtils.getEmail(principal), type));
    }


    public CategoryDto saveCategory(CategoryDto categoryDto, Principal principal) throws FunctionnalException {
        var category = categoryMapper.dtoToModel(categoryDto);
        category.setUserId(PrincipalUtils.getEmail(principal));
        return categoryMapper.modelToDto(categoryService.saveCategory(category));
    }


}
