package com.emu.apps.qcm.domain.repositories.adapters;

import com.emu.apps.qcm.domain.models.Category;
import com.emu.apps.qcm.domain.repositories.CategoryRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.infra.persistence.CategoryPersistencePort;
import org.springframework.stereotype.Service;


/**
 * Category Business Delegate
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
public class CategoryRepositoryAdapter implements CategoryRepository {

    private final CategoryPersistencePort categoryPersistencePort;

    public CategoryRepositoryAdapter(CategoryPersistencePort categoryPersistencePort) {
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
