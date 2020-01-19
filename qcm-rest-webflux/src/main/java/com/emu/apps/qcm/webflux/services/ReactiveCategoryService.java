package com.emu.apps.qcm.webflux.services;


import com.emu.apps.qcm.webflux.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveCategoryService {

    Mono <Category> findById(Long id);

    Flux <Category> findAll();

    Mono <Category> save(Category epic);

//    Mono <Category> findOrCreateByLibelle(String libelle);

}
