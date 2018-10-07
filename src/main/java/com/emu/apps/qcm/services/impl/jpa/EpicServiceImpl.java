package com.emu.apps.qcm.services.impl.jpa;

import com.emu.apps.qcm.services.entity.category.Category;
import com.emu.apps.qcm.services.repositories.CategoryRepository;
import com.emu.apps.qcm.services.CategoryService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by eric on 14/06/2017.
 */
@Service
public class EpicServiceImpl implements CategoryService {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CategoryRepository epicRepository;

    @Override
    public Category save(Category epic) {
        return epicRepository.save(epic);
    }

    @Override
    public Category findById(Long id) {
        return epicRepository.findOne(id);
    }

    @Override
    public Category findByLibelle(String libelle) {
        return epicRepository.findByLibelle(libelle);
    }

    @Override
    public Category findOrCreateByLibelle(String libelle) {
        Category epic = epicRepository.findByLibelle(libelle);
        if(epic == null ) {
            epic = save(new Category(libelle));
        }
        return epic;
    }


    @Override
    public Iterable<Category> findAll() {
        return epicRepository.findAll();
    }

}
