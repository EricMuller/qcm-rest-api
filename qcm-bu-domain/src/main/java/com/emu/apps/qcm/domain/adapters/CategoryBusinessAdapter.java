package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.api.models.Category;
import com.emu.apps.qcm.domain.ports.CategoryBusinessPort;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.spi.persistence.CategoryPersistencePort;
import org.springframework.stereotype.Service;


/**
 * Category Business Delegate
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
public class CategoryBusinessAdapter implements CategoryBusinessPort {

    private final CategoryPersistencePort categoryPersistencePort;

    public CategoryBusinessAdapter(CategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }


    @Override
    public Category getCategoryByUuid(String id) {
        return categoryPersistencePort.findByUuid(id).orElse(null);
    }


    @Override
    public Iterable <Category> getCategories(String principal, Type type) {
        return categoryPersistencePort.findCategories(principal, type);
    }


    @Override
    public Category saveCategory(Category category, String principal) {
        category.setUserId(principal);
        return categoryPersistencePort.saveCategory(category);
    }

}
