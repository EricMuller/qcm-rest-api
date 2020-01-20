package com.emu.apps.qcm.services.jpa;

import com.emu.apps.qcm.services.CategoryService;
import com.emu.apps.qcm.services.entity.category.Category;
import com.emu.apps.qcm.services.jpa.repositories.CategoryRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by eric on 14/06/2017.
 */
@Service
@Transactional()
public class CategoryServiceImpl implements CategoryService {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional <Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category findByLibelle(String libelle) {
        return categoryRepository.findByLibelle(libelle);
    }

    @Override
    public Category findOrCreateByLibelle(String libelle) {
        var category = categoryRepository.findByLibelle(libelle);

        return category == null ? save(new Category(libelle)) : category;
    }


    @Override
    public Iterable <Category> findAll() {
        return categoryRepository.findAll();
    }

}
