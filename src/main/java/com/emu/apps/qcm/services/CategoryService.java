package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.entity.category.Category;

public interface CategoryService {

    Category save(Category epic);

    Category findById(Long id);

    Category findByLibelle(String libelle);

    Category findOrCreateByLibelle(String libelle);

    Iterable<Category> findAll();


}
