package com.emu.apps.qcm.webflux.services;




import com.emu.apps.qcm.webflux.model.Category;
import com.emu.apps.qcm.webflux.repositories.ReactiveCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by eric on 14/06/2017.
 */
@Service
@Transactional()
public class ReactiveCategoryServiceImpl implements ReactiveCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveCategoryServiceImpl.class);

    private final ReactiveCategoryRepository categoryRepository;

    @Autowired
    public ReactiveCategoryServiceImpl(ReactiveCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Mono <Category> save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Mono <Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

//    public Mono <Category> findByLibelle(String libelle) {
//        return categoryRepository.findByLibelle(libelle);
//    }
//
//
//    public Mono <Category> findOrCreateByLibelle(String libelle) {
//        var category = categoryRepository.findByLibelle(libelle);
//
//        return category == null ? save(new Category(libelle)) : category;
//    }


    @Override
    public Flux <Category> findAll() {
        LOGGER.info("findAll start");
        return categoryRepository.findAll();
    }

}
