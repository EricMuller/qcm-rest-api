package com.emu.apps.qcm.infra.persistence;



import com.emu.apps.qcm.domain.model.category.MpttCategory;

import java.util.Optional;
import java.util.UUID;


public interface MpptCategoryPersistencePort {

    Optional <MpttCategory> findByUuid(String uuid);

    MpttCategory saveCategory(MpttCategory mpttCategory);

    MpttCategory findOrCreateByLibelle(String userId, String type, String libelle);

    MpttCategory findOrCreateChildByLibelle(UUID parentId, String type, String libelle);

    Iterable <MpttCategory> findCategories(String userId, String type);

    Iterable <MpttCategory> findChildrenCategories(UUID parentId);

}
