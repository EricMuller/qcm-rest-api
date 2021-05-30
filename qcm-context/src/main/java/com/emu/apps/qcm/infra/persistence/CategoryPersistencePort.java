package com.emu.apps.qcm.infra.persistence;



import com.emu.apps.qcm.domain.model.category.Category;

import java.util.Optional;
import java.util.UUID;


public interface CategoryPersistencePort {

    Optional <Category> findByUuid(String uuid);

    Category saveCategory(Category category);

    Category findOrCreateByLibelle(String userId, String type, String libelle);

    Category findOrCreateChildByLibelle(UUID parentId, String type, String libelle);

    Iterable <Category> findCategories(String userId, String type);

    Iterable <Category> findChildrenCategories(UUID parentId);

}
