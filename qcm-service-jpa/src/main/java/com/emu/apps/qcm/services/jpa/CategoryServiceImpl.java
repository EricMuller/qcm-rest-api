package com.emu.apps.qcm.services.jpa;

import com.emu.apps.qcm.services.CategoryService;
import com.emu.apps.qcm.services.entity.category.Category;
import com.emu.apps.qcm.services.entity.category.Category.Type;
import com.emu.apps.qcm.services.jpa.repositories.category.CategoryRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.emu.apps.qcm.services.entity.category.Category.Type.QUESTION;

/**
 * Created by eric on 14/06/2017.
 */
@Service
@Transactional()
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional <Category> findById(Long id) {
        return categoryRepository.findById(id);
    }


    @Override
    public Category findOrCreateByLibelle(Type type, String libelle) {
        var category = categoryRepository.findByLibelle(libelle);
        return category == null ? saveCategory(new Category(QUESTION, libelle)) : category;
    }

    @Override
    public Iterable <Category> findCategories(Specification <Category> specification) {
        return categoryRepository.findAll(specification);
    }

}
