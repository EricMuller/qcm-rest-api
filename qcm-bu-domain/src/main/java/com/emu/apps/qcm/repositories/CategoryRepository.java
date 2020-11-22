package com.emu.apps.qcm.repositories;

import com.emu.apps.qcm.aggregates.Category;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.spi.persistence.exceptions.FunctionnalException;

public interface CategoryRepository {

    Category getCategoryByUuid(String id);

    Iterable <Category> getCategories(String principal, Type type) throws FunctionnalException;

    Category saveCategory(Category category, String principal) ;

}
