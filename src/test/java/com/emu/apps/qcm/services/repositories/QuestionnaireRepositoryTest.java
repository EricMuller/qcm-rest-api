package com.emu.apps.qcm.services.repositories;

import com.emu.apps.qcm.services.FixtureService;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.questions.Response;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.projections.QuestionnaireProjection;
import com.google.common.collect.Iterables;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QuestionnaireRepositoryTest {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private FixtureService questionnaireFixture;

    @Test
    @Transactional
    public void findOne() {

        Questionnaire q = questionnaireFixture.createQuestionQuestionnaireTag();

        Questionnaire questionnaire = questionnaireRepository.findOne(q.getId());

        Assert.assertNotNull(questionnaire.getId());
        Assert.assertNotNull(questionnaire.getEpic());
        Assert.assertEquals(questionnaire.getEpic().getLibelle(), FixtureService.CATEGORIE_LIBELLE);
        Assert.assertNotNull(questionnaire.getDescription());
        Assert.assertEquals(questionnaire.getDescription(), FixtureService.QUESTIONNAIRE_DESC);

        Assert.assertEquals(questionnaire.getQuestionnaireQuestions().size(), 2);

        QuestionnaireQuestion questionnaireQuestion1 = Iterables.getFirst(questionnaire.getQuestionnaireQuestions(), null);

        Assert.assertNotNull(questionnaireQuestion1);
        Assert.assertNotNull(questionnaireQuestion1.getQuestion());
        Assert.assertEquals(questionnaireQuestion1.getQuestion().getResponses().size(), 2);

        Response response1 = Iterables.getFirst(questionnaireQuestion1.getQuestion().getResponses(), null);
        Assert.assertEquals(FixtureService.RESPONSE_RESPONSE_1, response1.getResponse());

        Question question = questionnaireQuestion1.getQuestion();
        //tags
        Assertions.assertThat(question.getQuestionTags()).isNotNull();


        QuestionTag questionTag = Iterables.getFirst(question.getQuestionTags(), null);
        Assertions.assertThat(questionTag.getTag()).isNotNull();
        Assertions.assertThat(questionTag.getTag().getLibelle()).isNotNull().startsWith(FixtureService.TAG_LIBELLE_1.substring(0,3));

    }

    @Test
    public void findQuestionnaireById() {

        Questionnaire q = questionnaireFixture.createQuestionQuestionnaireTag();

        QuestionnaireProjection questionnaire = questionnaireRepository.findQuestionnaireById(q.getId());

        Assert.assertNotNull(questionnaire.getId());
        Assert.assertNotNull(questionnaire.getEpic());
        Assert.assertEquals(questionnaire.getEpic().getLibelle(), FixtureService.CATEGORIE_LIBELLE);
        Assert.assertNotNull(questionnaire.getDescription());
        Assert.assertEquals(questionnaire.getDescription(), FixtureService.QUESTIONNAIRE_DESC);
    }

}