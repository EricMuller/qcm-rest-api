package com.emu.apps.qcm.services.repositories;

import com.emu.apps.qcm.ApplicationTest;
import com.emu.apps.qcm.services.FixtureService;
import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.questions.Response;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.entity.tags.Tag;
import com.emu.apps.qcm.services.projections.QuestionnaireProjection;
import com.emu.apps.qcm.services.repositories.specifications.questionnaire.QuestionnaireSpecification;
import com.emu.apps.qcm.web.rest.dtos.FilterDto;
import com.google.common.collect.Iterables;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class QuestionnaireRepositoryTest {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private FixtureService questionnaireFixture;

    @Autowired
    private QuestionnaireSpecification questionnaireSpecification;

    @Test
    @Transactional
    public void findOne() {

        Questionnaire q = questionnaireFixture.createQuestionQuestionnaireTag();

        Questionnaire questionnaire = questionnaireRepository.findOne(q.getId());

        Assert.assertNotNull(questionnaire.getId());
        Assert.assertNotNull(questionnaire.getCategory());
        Assert.assertEquals(questionnaire.getCategory().getLibelle(), FixtureService.CATEGORIE_LIBELLE);
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
        Assertions.assertThat(questionTag.getTag().getLibelle()).isNotNull().startsWith(FixtureService.TAG_LIBELLE_1.substring(0, 3));

    }

    @Test
    public void findQuestionnaireById() {

        Questionnaire q = questionnaireFixture.createQuestionQuestionnaireTag();

        QuestionnaireProjection questionnaire = questionnaireRepository.findQuestionnaireById(q.getId());

        Assert.assertNotNull(questionnaire.getId());
        Assert.assertNotNull(questionnaire.getCategory());
        Assert.assertEquals(questionnaire.getCategory().getLibelle(), FixtureService.CATEGORIE_LIBELLE);
        Assert.assertNotNull(questionnaire.getDescription());
        Assert.assertEquals(questionnaire.getDescription(), FixtureService.QUESTIONNAIRE_DESC);
    }

    @Test
    public void findAllWithSpecification() {

        questionnaireFixture.createQuestionQuestionnaireTag();

        Tag tag = questionnaireFixture.findTagbyLibelle(questionnaireFixture.TAG_LIBELLE_4);
        Assertions.assertThat(tag).isNotNull();

        FilterDto filterDto = new FilterDto("tag_id", tag.getId());

        Specification<Questionnaire> specification = questionnaireSpecification.getFilter(Arrays.asList(filterDto).toArray(new FilterDto[0]), new Principal() {
            @Override
            public String getName() {
                return ApplicationTest.USER_TEST;
            }
        });

        Page<Questionnaire> page = questionnaireRepository.findAll(specification, (Pageable) null);
        Assertions.assertThat(page).isNotNull();

        Assertions.assertThat(page.getNumberOfElements()).isEqualTo(1);
    }

}