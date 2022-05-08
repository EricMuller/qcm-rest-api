package com.emu.apps.qcm.domain.model.category;


import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.shared.exceptions.FunctionnalException;

public interface MpttCategoryRepository {

    MpttCategory getCategoryByUuid(String id);

    Iterable <MpttCategory> getCategories(PrincipalId principal, String type) throws FunctionnalException;

    MpttCategory saveCategory(MpttCategory category, PrincipalId principal);

}
