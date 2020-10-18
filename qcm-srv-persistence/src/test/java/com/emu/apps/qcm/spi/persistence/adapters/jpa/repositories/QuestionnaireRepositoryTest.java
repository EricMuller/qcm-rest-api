package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.spi.infrastructure.DbFixture;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.ResponseEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionTagEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.TagEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.projections.QuestionnaireProjection;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.specifications.QuestionnaireSpecificationBuilder;
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

@SpringBootTest(classes = SpringBootJpaTestConfig.class)
@ActiveProfiles(value = "test")
public class QuestionnaireRepositoryTest {

    @Autowired
    private DbFixture dbFixture;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;


    @Test
    @Transactional
    public void findOne() {

        dbFixture.emptyDatabase();

        QuestionnaireEntity questionnaire1 = dbFixture.createOneQuestionnaireWithTwoQuestionTags();

        QuestionnaireEntity questionnaire = questionnaireRepository.findById(questionnaire1.getId()).orElse(null);

        assertNotNull(questionnaire);
        assertNotNull(questionnaire.getId());
        assertNotNull(questionnaire.getCategory());
        assertEquals(DbFixture.CATEGORIE_LIBELLE, questionnaire.getCategory().getLibelle());
        assertNotNull(questionnaire.getDescription());
        assertEquals(DbFixture.QUESTIONNAIRE_DESC, questionnaire.getDescription());

        assertEquals(2, questionnaire.getQuestionnaireQuestions().size());

        QuestionnaireQuestionEntity questionnaireQuestion1 = Iterables.getFirst(questionnaire.getQuestionnaireQuestions(), null);

        assertNotNull(questionnaireQuestion1);
        assertNotNull(questionnaireQuestion1.getQuestion());
        assertEquals(2, questionnaireQuestion1.getQuestion().getResponses().size());

        ResponseEntity response1 = Iterables.getFirst(questionnaireQuestion1.getQuestion().getResponses(), null);
        assertEquals(DbFixture.RESPONSE_RESPONSE_1, response1.getResponseText());

        QuestionEntity question = questionnaireQuestion1.getQuestion();
        //tags
        Assertions.assertThat(question.getQuestionTags()).isNotNull();


        QuestionTagEntity questionTag = Iterables.getFirst(question.getQuestionTags(), null);
        Assertions.assertThat(questionTag.getTag()).isNotNull();
        Assertions.assertThat(questionTag.getTag().getLibelle()).isNotNull().startsWith(DbFixture.QUESTION_TAG_LIBELLE_1.substring(0, 3));

    }

    @Test
    public void findQuestionnaireById() {

        dbFixture.emptyDatabase();

        QuestionnaireEntity q = dbFixture.createOneQuestionnaireWithTwoQuestionTags();

        QuestionnaireProjection questionnaire = questionnaireRepository.findQuestionnaireById(q.getId());

        assertNotNull(questionnaire.getUuid());
        assertNotNull(questionnaire.getCategory());
        assertEquals(DbFixture.CATEGORIE_LIBELLE, questionnaire.getCategory().getLibelle());
        assertNotNull(questionnaire.getTitle());
        assertEquals(DbFixture.QUESTIONNAIRE_TITLE, questionnaire.getTitle());
    }

    @Test
    public void findAllWithSpecification() {

        dbFixture.emptyDatabase();

        dbFixture.createOneQuestionnaireWithTwoQuestionTags();

        TagEntity tag = dbFixture.findTagbyLibelle(dbFixture.QUESTIONNAIRE_TAG_LIBELLE_1, () ->  SpringBootJpaTestConfig.USER_TEST);
        Assertions.assertThat(tag).isNotNull();

        QuestionnaireSpecificationBuilder questionnaireSpecificationBuilder = new QuestionnaireSpecificationBuilder();
        questionnaireSpecificationBuilder.setTagUuids(new UUID[]{tag.getUuid()});

        questionnaireSpecificationBuilder.setPrincipal(SpringBootJpaTestConfig.USER_TEST);

        Pageable pageable = PageRequest.of(0, 3, Sort.by("id"));

        Page <QuestionnaireEntity> page = questionnaireRepository.findAll(questionnaireSpecificationBuilder.build(), pageable);
        Assertions.assertThat(page).isNotNull();

        Assertions.assertThat(page.getNumberOfElements()).isEqualTo(1);
    }

}
