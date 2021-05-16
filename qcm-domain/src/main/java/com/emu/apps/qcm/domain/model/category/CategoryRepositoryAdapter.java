package com.emu.apps.qcm.domain.model.category;


import com.emu.apps.qcm.domain.model.base.PrincipalId;
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
    public Iterable <Category> getCategories(PrincipalId principal, String type) {
        return categoryPersistencePort.findCategories(principal.toUUID(), type);
    }


    @Override
    public Category saveCategory(Category category, PrincipalId principal) {
        category.setUserId(principal.toUUID());
        return categoryPersistencePort.saveCategory(category);
    }

}
