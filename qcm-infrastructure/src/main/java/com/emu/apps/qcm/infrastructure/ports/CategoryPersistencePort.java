package com.emu.apps.qcm.infrastructure.ports;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Type;
import com.emu.apps.qcm.infrastructure.exceptions.TechnicalException;
import com.emu.apps.qcm.domain.dtos.CategoryDto;

import java.util.Optional;
import java.util.UUID;


public interface CategoryPersistencePort {

    Optional <CategoryDto> findByUuid(String uuid);

    CategoryDto saveCategory(CategoryDto category) ;

    CategoryDto findOrCreateByLibelle(String userId, Type type, String libelle) throws TechnicalException;

    CategoryDto findOrCreateChildByLibelle(UUID parentId, Type type, String libelle) throws TechnicalException;

    Iterable <CategoryDto> findCategories(String userId, Type type) ;

    Iterable <CategoryDto> findChildrenCategories(UUID parentId) ;

}
