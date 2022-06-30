package com.emu.apps.qcm.domain.model.questionnaire;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.tag.Tag;
import com.emu.apps.qcm.domain.model.tag.TagId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface QuestionnaireRepository {

    boolean existsOfId(QuestionnaireId questionnaireId);

    Optional <Questionnaire> getQuestionnaireOfId(QuestionnaireId questionnaireId);

    ResponseEntity <Questionnaire> deleteQuestionnaireOfId(QuestionnaireId questionnaireId);

    Page <QuestionnaireQuestion> getQuestionsByQuestionnaireOfId(QuestionnaireId questionnaireId, Pageable pageable);

    Iterable <QuestionnaireQuestion> getQuestionsByQuestionnaireOfId(QuestionnaireId questionnaireId);

    QuestionnaireQuestion addQuestion(QuestionnaireId questionnaireId, QuestionId questionId, Optional <Integer> position, PrincipalId principal);

    List <QuestionnaireQuestion> addQuestions(QuestionnaireId questionnaireId, Collection <Question> questions, PrincipalId principal);

    Questionnaire updateQuestionnaire(Questionnaire questionnaire, PrincipalId principal);

    Questionnaire saveQuestionnaire(Questionnaire questionnaire, PrincipalId principal);

    void deleteQuestion(QuestionnaireId questionnaireId, QuestionId questionId);

    QuestionnaireQuestion getQuestion(QuestionnaireId questionnaireId, QuestionId questionId);

    void activateQuestionnaire(QuestionnaireId questionnaireId);

    QuestionnaireAggregate getQuestionnaireAggregateOfId(QuestionnaireId questionnaireId) ;

    Page <Tag> getTags(String search, Pageable pageable, PrincipalId principal) throws IOException;

    Tag getTagOfId(TagId tagId);

    Tag findOrCreateTagByLibelle(String libelle, PrincipalId principal) ;

}
