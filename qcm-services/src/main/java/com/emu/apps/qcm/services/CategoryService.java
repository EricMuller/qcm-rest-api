package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.jpa.entity.category.Category;

import java.util.Optional;


public interface CategoryService {

    Category save(Category epic);

    Optional<Category> findById(Long id);

    Category findByLibelle(String libelle);

    Category findOrCreateByLibelle(String libelle);

    Iterable<Category> findAll();

}
