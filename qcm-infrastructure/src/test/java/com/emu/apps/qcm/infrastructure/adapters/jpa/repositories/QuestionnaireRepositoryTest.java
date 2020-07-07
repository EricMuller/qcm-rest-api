package com.emu.apps.qcm.infrastructure.adapters.jpa.repositories;

import com.emu.apps.qcm.infrastructure.Fixture;
import com.emu.apps.qcm.infrastructure.adapters.jpa.config.SpringBootTestConfig;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Question;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Response;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.Tag;
import com.emu.apps.qcm.infrastructure.adapters.jpa.projections.QuestionnaireProjection;
import com.emu.apps.qcm.infrastructure.adapters.jpa.specifications.QuestionnaireSpecificationBuilder;
import com.google.common.collect.Iterables;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = SpringBootTestConfig.class)
@ActiveProfiles(value = "test")
public class QuestionnaireRepositoryTest {

    @Autowired
    private Fixture fixture;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;


    @Test
    @Transactional
    public void findOne() {

        fixture.emptyDatabase();

        Questionnaire questionnaire1 = fixture.createOneQuestionnaireWithTwoQuestionTags();

        Questionnaire questionnaire = questionnaireRepository.findById(questionnaire1.getId()).orElse(null);

        assertNotNull(questionnaire);
        assertNotNull(questionnaire.getId());
        assertNotNull(questionnaire.getCategory());
        assertEquals(Fixture.CATEGORIE_LIBELLE, questionnaire.getCategory().getLibelle());
        assertNotNull(questionnaire.getDescription());
        assertEquals(Fixture.QUESTIONNAIRE_DESC, questionnaire.getDescription());

        assertEquals(2, questionnaire.getQuestionnaireQuestions().size());

        QuestionnaireQuestion questionnaireQuestion1 = Iterables.getFirst(questionnaire.getQuestionnaireQuestions(), null);

        assertNotNull(questionnaireQuestion1);
        assertNotNull(questionnaireQuestion1.getQuestion());
        assertEquals(2, questionnaireQuestion1.getQuestion().getResponses().size());

        Response response1 = Iterables.getFirst(questionnaireQuestion1.getQuestion().getResponses(), null);
        assertEquals(Fixture.RESPONSE_RESPONSE_1, response1.getResponse());

        Question question = questionnaireQuestion1.getQuestion();
        //tags
        Assertions.assertThat(question.getQuestionTags()).isNotNull();


        QuestionTag questionTag = Iterables.getFirst(question.getQuestionTags(), null);
        Assertions.assertThat(questionTag.getTag()).isNotNull();
        Assertions.assertThat(questionTag.getTag().getLibelle()).isNotNull().startsWith(Fixture.QUESTION_TAG_LIBELLE_1.substring(0, 3));

    }

    @Test
    public void findQuestionnaireById() {

        fixture.emptyDatabase();

        Questionnaire q = fixture.createOneQuestionnaireWithTwoQuestionTags();

        QuestionnaireProjection questionnaire = questionnaireRepository.findQuestionnaireById(q.getId());

        assertNotNull(questionnaire.getUuid());
        assertNotNull(questionnaire.getCategory());
        assertEquals(Fixture.CATEGORIE_LIBELLE, questionnaire.getCategory().getLibelle());
        assertNotNull(questionnaire.getTitle());
        assertEquals(Fixture.QUESTIONNAIRE_TITLE, questionnaire.getTitle());
    }

    @Test
    public void findAllWithSpecification() {


        fixture.emptyDatabase();

        fixture.createOneQuestionnaireWithTwoQuestionTags();

        Tag tag = fixture.findTagbyLibelle(fixture.QUESTIONNAIRE_TAG_LIBELLE_1, () -> SpringBootTestConfig.USER_TEST);
        Assertions.assertThat(tag).isNotNull();

        QuestionnaireSpecificationBuilder questionnaireSpecificationBuilder = new QuestionnaireSpecificationBuilder();
        questionnaireSpecificationBuilder.setTagUuids(new UUID[]{tag.getUuid()});

        questionnaireSpecificationBuilder.setPrincipal(SpringBootTestConfig.USER_TEST);

        Pageable pageable = PageRequest.of(0, 3, Sort.by("id"));

        Page <Questionnaire> page = questionnaireRepository.findAll(questionnaireSpecificationBuilder.build(), pageable);
        Assertions.assertThat(page).isNotNull();

        Assertions.assertThat(page.getNumberOfElements()).isEqualTo(1);
    }

}
