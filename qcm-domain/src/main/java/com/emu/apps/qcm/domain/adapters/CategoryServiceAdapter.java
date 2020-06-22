package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.models.CategoryDto;
import com.emu.apps.qcm.domain.ports.CategoryServicePort;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.infrastructure.ports.CategoryPersistencePort;
import org.springframework.stereotype.Service;


/**
 * Category Business Delegate
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
public class CategoryServiceAdapter implements CategoryServicePort {

    private final CategoryPersistencePort categoryPersistencePort;

    public CategoryServiceAdapter(CategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }


    @Override
    public CategoryDto getCategoryByUuid(String id) {
        return categoryPersistencePort.findByUuid(id).orElse(null);
    }


    @Override
    public Iterable <CategoryDto> getCategories(String principal, Type type) {
        return categoryPersistencePort.findCategories(principal, type);
    }


    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto, String principal) {
        categoryDto.setUserId(principal);
        return categoryPersistencePort.saveCategory(categoryDto);
    }

}
