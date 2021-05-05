package com.emu.apps.qcm.domain.model;


import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.shared.exceptions.FunctionnalException;

public interface CategoryRepository {

    Category getCategoryByUuid(String id);

    Iterable <Category> getCategories(PrincipalId principal, String type) throws FunctionnalException;

    Category saveCategory(Category category, PrincipalId principal);

}
