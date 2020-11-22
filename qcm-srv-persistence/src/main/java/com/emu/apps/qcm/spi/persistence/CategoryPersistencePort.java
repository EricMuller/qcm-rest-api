package com.emu.apps.qcm.spi.persistence;

import com.emu.apps.qcm.aggregates.Category;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type;

import java.util.Optional;
import java.util.UUID;


public interface CategoryPersistencePort {

    Optional <Category> findByUuid(String uuid);

    Category saveCategory(Category category) ;

    Category findOrCreateByLibelle(String userId, Type type, String libelle) ;

    Category findOrCreateChildByLibelle(UUID parentId, Type type, String libelle) ;

    Iterable <Category> findCategories(String userId, Type type) ;

    Iterable <Category> findChildrenCategories(UUID parentId) ;

}
