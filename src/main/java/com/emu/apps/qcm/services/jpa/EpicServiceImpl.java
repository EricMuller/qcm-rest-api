package com.emu.apps.qcm.services.jpa;

import com.emu.apps.qcm.services.jpa.entity.category.Category;
import com.emu.apps.qcm.services.jpa.repositories.CategoryRepository;
import com.emu.apps.qcm.services.CategoryService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Optional<Category> findById(Long id) {
        return epicRepository.findById(id);
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
