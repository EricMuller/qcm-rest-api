package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.models.Category;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.infra.persistence.exceptions.FunctionnalException;

public interface CategoryRepository {

    Category getCategoryByUuid(String id);

    Iterable <Category> getCategories(String principal, Type type) throws FunctionnalException;

    Category saveCategory(Category category, String principal) ;

}