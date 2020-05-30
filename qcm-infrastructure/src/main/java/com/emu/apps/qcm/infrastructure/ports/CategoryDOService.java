package com.emu.apps.qcm.infrastructure.ports;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Category;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.infrastructure.exceptions.FunctionnalException;
import com.emu.apps.qcm.infrastructure.exceptions.TechnicalException;

import java.util.Optional;


public interface CategoryDOService {

    Optional <Category> findById(Long id);

    Category saveCategory(Category category) throws FunctionnalException;

    Category findOrCreateByLibelle(String userId, Type type, String libelle) throws TechnicalException;

    Category findOrCreateChildByLibelle(Long parentId, Type type, String libelle) throws TechnicalException;

    Iterable <Category> findCategories(String userId, Type type) throws FunctionnalException;

    Iterable <Category> findChildrenCategories(long parentId) ;

}
