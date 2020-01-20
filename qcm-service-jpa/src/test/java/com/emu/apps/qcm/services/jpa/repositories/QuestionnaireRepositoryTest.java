package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.qcm.H2TestProfileJPAConfig;
import com.emu.apps.qcm.services.FixtureService;
import com.emu.apps.qcm.services.SpringBootTestCase;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.questions.Response;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.entity.tags.Tag;
import com.emu.apps.qcm.services.jpa.projections.QuestionnaireProjection;
import com.emu.apps.qcm.services.jpa.specifications.QuestionnaireSpecificationBuilder;
import com.google.common.collect.Iterables;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuestionnaireRepositoryTest extends SpringBootTestCase {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Test
    @Transactional
    public void findOne() {

        Questionnaire questionnaire1 = getFixtureService().createOneQuestionnaireWithTwoQuestionTags();

        Questionnaire questionnaire = questionnaireRepository.findById(questionnaire1.getId()).orElse(null);

        assertNotNull(questionnaire);
        assertNotNull(questionnaire.getId());
        assertNotNull(questionnaire.getCategory());
        assertEquals(FixtureService.CATEGORIE_LIBELLE, questionnaire.getCategory().getLibelle());
        assertNotNull(questionnaire.getDescription());
        assertEquals(FixtureService.QUESTIONNAIRE_DESC, questionnaire.getDescription());

        assertEquals(2, questionnaire.getQuestionnaireQuestions().size());

        QuestionnaireQuestion questionnaireQuestion1 = Iterables.getFirst(questionnaire.getQuestionnaireQuestions(), null);

        assertNotNull(questionnaireQuestion1);
        assertNotNull(questionnaireQuestion1.getQuestion());
        assertEquals(2, questionnaireQuestion1.getQuestion().getResponses().size());

        Response response1 = Iterables.getFirst(questionnaireQuestion1.getQuestion().getResponses(), null);
        assertEquals(FixtureService.RESPONSE_RESPONSE_1, response1.getResponse());

        Question question = questionnaireQuestion1.getQuestion();
        //tags
        Assertions.assertThat(question.getQuestionTags()).isNotNull();


        QuestionTag questionTag = Iterables.getFirst(question.getQuestionTags(), null);
        Assertions.assertThat(questionTag.getTag()).isNotNull();
        Assertions.assertThat(questionTag.getTag().getLibelle()).isNotNull().startsWith(FixtureService.QUESTION_TAG_LIBELLE_1.substring(0, 3));

    }

    @Test
    public void findQuestionnaireById() {

        Questionnaire q = getFixtureService().createOneQuestionnaireWithTwoQuestionTags();

        QuestionnaireProjection questionnaire = questionnaireRepository.findQuestionnaireById(q.getId());

        assertNotNull(questionnaire.getId());
        assertNotNull(questionnaire.getCategory());
        assertEquals(FixtureService.CATEGORIE_LIBELLE, questionnaire.getCategory().getLibelle());
        assertNotNull(questionnaire.getDescription());
        assertEquals(FixtureService.QUESTIONNAIRE_DESC, questionnaire.getDescription());
    }

    @Test
    public void findAllWithSpecification() {


        getFixtureService().createOneQuestionnaireWithTwoQuestionTags();

        Tag tag = getFixtureService().findTagbyLibelle(getFixtureService().QUESTIONNAIRE_TAG_LIBELLE_1, getPrincipal());
        Assertions.assertThat(tag).isNotNull();

        QuestionnaireSpecificationBuilder questionnaireSpecificationBuilder = new QuestionnaireSpecificationBuilder();
        questionnaireSpecificationBuilder.setTagIds(new Long[]{tag.getId()});

        questionnaireSpecificationBuilder.setPrincipal(H2TestProfileJPAConfig.USER_TEST);

        Pageable pageable = PageRequest.of(0, 3, Sort.by("id"));

        Page<Questionnaire> page = questionnaireRepository.findAll(questionnaireSpecificationBuilder.build(), pageable);
        Assertions.assertThat(page).isNotNull();

        Assertions.assertThat(page.getNumberOfElements()).isEqualTo(1);
    }

}
