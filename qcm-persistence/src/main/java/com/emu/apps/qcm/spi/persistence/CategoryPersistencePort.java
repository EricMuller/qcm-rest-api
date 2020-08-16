package com.emu.apps.qcm.spi.persistence;

import com.emu.apps.qcm.api.models.Category;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.spi.persistence.exceptions.TechnicalException;

import java.util.Optional;
import java.util.UUID;


public interface CategoryPersistencePort {

    Optional <Category> findByUuid(String uuid);

    Category saveCategory(Category category) ;

    Category findOrCreateByLibelle(String userId, Type type, String libelle) throws TechnicalException;

    Category findOrCreateChildByLibelle(UUID parentId, Type type, String libelle) throws TechnicalException;

    Iterable <Category> findCategories(String userId, Type type) ;

    Iterable <Category> findChildrenCategories(UUID parentId) ;

}
