package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.entity.category.Category;
import com.emu.apps.qcm.services.entity.category.QuestionCategory;
import com.emu.apps.qcm.services.entity.category.QuestionnaireCategory;

import java.util.Optional;


public interface CategoryService {

    Optional<Category> findById(Long id);

    Category findByLibelle(String libelle);

    Iterable<QuestionCategory> findQuestionCategories();

    QuestionCategory findOrCreateByLibelle(String libelle);

    QuestionCategory saveQuestionCategory(QuestionCategory questionCategory);

    Iterable <QuestionnaireCategory> findQuestionnairesCategories();

    QuestionnaireCategory saveQuestionnaireCategory(QuestionnaireCategory questionnaireCategory);

}
