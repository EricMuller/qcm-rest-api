package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.api.models.Category;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.spi.persistence.exceptions.FunctionnalException;

public interface CategoryBusinessPort {

    Category getCategoryByUuid(String id);

    Iterable <Category> getCategories(String principal, Type type) throws FunctionnalException;

    Category saveCategory(Category category, String principal) throws FunctionnalException;

}
