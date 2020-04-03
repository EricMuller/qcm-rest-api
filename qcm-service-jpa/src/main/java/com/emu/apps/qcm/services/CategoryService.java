package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.entity.category.Category;
import com.emu.apps.qcm.services.entity.category.Type;
import com.emu.apps.qcm.services.exceptions.FunctionnalException;

import java.util.Optional;


public interface CategoryService {

    Optional <Category> findById(Long id);

    Category saveCategory(Category category) throws FunctionnalException;

    Category findOrCreateByLibelle(String userId, Type type, String libelle) throws FunctionnalException;

    Category findOrCreateChildByLibelle(Long parentId, Type type, String libelle) throws FunctionnalException;

    Iterable <Category> findCategories(String userId, Type type) throws FunctionnalException;

    Iterable <Category> findChildrenCategories(long parentId) ;

}
