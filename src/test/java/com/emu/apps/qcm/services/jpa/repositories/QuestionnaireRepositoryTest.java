package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.Application;
import com.emu.apps.H2TestProfileJPAConfig;
import com.emu.apps.qcm.services.FixtureService;
import com.emu.apps.qcm.services.FixtureTest;
import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.services.jpa.entity.questions.Response;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import com.emu.apps.qcm.services.jpa.projections.QuestionnaireProjection;
import com.emu.apps.qcm.services.jpa.specifications.QuestionnaireSpecificationBuilder;
import com.google.common.collect.Iterables;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class QuestionnaireRepositoryTest extends FixtureTest {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Test
    @Transactional
    public void findOne() {

        Questionnaire q = fixtureService.createOneQuestionnaireWithTwoQuestionTags();

        Questionnaire questionnaire = questionnaireRepository.findById(q.getId()).orElse(null);

        Assert.assertNotNull(questionnaire);
        Assert.assertNotNull(questionnaire.getId());
        Assert.assertNotNull(questionnaire.getCategory());
        Assert.assertEquals(FixtureService.CATEGORIE_LIBELLE, questionnaire.getCategory().getLibelle());
        Assert.assertNotNull(questionnaire.getDescription());
        Assert.assertEquals(FixtureService.QUESTIONNAIRE_DESC, questionnaire.getDescription());

        Assert.assertEquals(2, questionnaire.getQuestionnaireQuestions().size());

        QuestionnaireQuestion questionnaireQuestion1 = Iterables.getFirst(questionnaire.getQuestionnaireQuestions(), null);

        Assert.assertNotNull(questionnaireQuestion1);
        Assert.assertNotNull(questionnaireQuestion1.getQuestion());
        Assert.assertEquals(2, questionnaireQuestion1.getQuestion().getResponses().size());

        Response response1 = Iterables.getFirst(questionnaireQuestion1.getQuestion().getResponses(), null);
        Assert.assertEquals(FixtureService.RESPONSE_RESPONSE_1, response1.getResponse());

        Question question = questionnaireQuestion1.getQuestion();
        //tags
        Assertions.assertThat(question.getQuestionTags()).isNotNull();


        QuestionTag questionTag = Iterables.getFirst(question.getQuestionTags(), null);
        Assertions.assertThat(questionTag.getTag()).isNotNull();
        Assertions.assertThat(questionTag.getTag().getLibelle()).isNotNull().startsWith(FixtureService.QUESTION_TAG_LIBELLE_1.substring(0, 3));

    }

    @Test
    public void findQuestionnaireById() {

        Questionnaire q = fixtureService.createOneQuestionnaireWithTwoQuestionTags();

        QuestionnaireProjection questionnaire = questionnaireRepository.findQuestionnaireById(q.getId());

        Assert.assertNotNull(questionnaire.getId());
        Assert.assertNotNull(questionnaire.getCategory());
        Assert.assertEquals(FixtureService.CATEGORIE_LIBELLE, questionnaire.getCategory().getLibelle());
        Assert.assertNotNull(questionnaire.getDescription());
        Assert.assertEquals(FixtureService.QUESTIONNAIRE_DESC, questionnaire.getDescription());
    }

    @Test
    public void findAllWithSpecification() {


        fixtureService.createOneQuestionnaireWithTwoQuestionTags();

        Tag tag = fixtureService.findTagbyLibelle(fixtureService.QUESTIONNAIRE_TAG_LIBELLE_1, getPrincipal());
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
