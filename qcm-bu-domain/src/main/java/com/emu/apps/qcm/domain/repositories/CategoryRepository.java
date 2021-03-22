package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.models.Category;
import com.emu.apps.qcm.domain.models.base.PrincipalId;
import com.emu.apps.shared.exceptions.FunctionnalException;

public interface CategoryRepository {

    Category getCategoryByUuid(String id);

    Iterable <Category> getCategories(PrincipalId principal, String type) throws FunctionnalException;

    Category saveCategory(Category category, PrincipalId principal);

}
