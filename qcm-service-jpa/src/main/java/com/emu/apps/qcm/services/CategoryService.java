package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.entity.category.Category;
import com.emu.apps.qcm.services.entity.category.QuestionCategory;
import com.emu.apps.qcm.services.entity.category.QuestionnaireCategory;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;


public interface CategoryService {

    Optional <Category> findById(Long id);

    Category findByLibelle(String libelle);

    QuestionCategory findOrCreateByLibelle(String libelle);

    QuestionCategory saveQuestionCategory(QuestionCategory questionCategory);

    QuestionnaireCategory saveQuestionnaireCategory(QuestionnaireCategory questionnaireCategory);

    Iterable <QuestionnaireCategory> findQuestionnairesCategories(Specification <QuestionnaireCategory> specification);

    Iterable <QuestionCategory> findQuestionCategories(Specification <QuestionCategory> specification);

}
