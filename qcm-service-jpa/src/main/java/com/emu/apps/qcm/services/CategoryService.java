package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.entity.category.Category;
import com.emu.apps.qcm.services.entity.category.Category.Type;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;


public interface CategoryService {

    Optional <Category> findById(Long id);

    Category saveCategory(Category category);


    Category findOrCreateByLibelle(Type type, String libelle);

    Iterable <Category> findCategories(Specification <Category> specification);

}
