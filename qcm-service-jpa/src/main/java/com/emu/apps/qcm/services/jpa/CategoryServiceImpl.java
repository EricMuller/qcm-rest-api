package com.emu.apps.qcm.services.jpa;

import com.emu.apps.qcm.services.CategoryService;
import com.emu.apps.qcm.services.entity.category.Category;
import com.emu.apps.qcm.services.entity.category.QuestionCategory;
import com.emu.apps.qcm.services.entity.category.QuestionnaireCategory;
import com.emu.apps.qcm.services.jpa.repositories.category.CategoryRepository;
import com.emu.apps.qcm.services.jpa.repositories.category.QuestionCategoryRepository;
import com.emu.apps.qcm.services.jpa.repositories.category.QuestionnaireCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by eric on 14/06/2017.
 */
@Service
@Transactional()
public class CategoryServiceImpl implements CategoryService {

    private final QuestionCategoryRepository questionCategoryRepository;

    private final QuestionnaireCategoryRepository questionnaireCategoryRepository;

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(QuestionCategoryRepository questionCategoryRepository, QuestionnaireCategoryRepository questionnaireCategoryRepository, CategoryRepository categoryRepository) {
        this.questionCategoryRepository = questionCategoryRepository;
        this.questionnaireCategoryRepository = questionnaireCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public QuestionCategory saveQuestionCategory(QuestionCategory questionCategory) {
        return questionCategoryRepository.save(questionCategory);
    }

    @Override
    public QuestionnaireCategory saveQuestionnaireCategory(QuestionnaireCategory questionnaireCategory) {
        return questionnaireCategoryRepository.save(questionnaireCategory);
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
    public QuestionCategory findOrCreateByLibelle(String libelle) {
        var category = questionCategoryRepository.findByLibelle(libelle);
        return category == null ? saveQuestionCategory(new QuestionCategory(libelle)) : category;
    }


    @Override
    public Iterable <QuestionnaireCategory> findQuestionnairesCategories() {
        return questionnaireCategoryRepository.findAll();
    }

    @Override
    public Iterable <QuestionCategory> findQuestionCategories() {
        return questionCategoryRepository.findAll();
    }

}
