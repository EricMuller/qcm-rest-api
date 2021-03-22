package com.emu.apps.qcm.infra.persistence.mappers;

import com.emu.apps.qcm.domain.models.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.models.questionnaire.QuestionnaireTag;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {QuestionnaireMapperImpl.class, CategoryMapperImpl.class, QuestionnaireTagMapperImpl.class, UuidMapperImpl.class})
@Tag("MapstructTest")
class QuestionnaireMapperTest {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Test
    void modelToDto() {

        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setUuid(UUID.randomUUID());

        Questionnaire questionnaire = questionnaireMapper.modelToDto(questionnaireEntity);

        Assertions.assertNotNull(questionnaire);
    }

    @Test
    void dtoToModel() {

        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setUuid(UUID.randomUUID().toString());

        QuestionnaireTag questionnaireTag = new QuestionnaireTag();
        questionnaireTag.setUuid(UUID.randomUUID().toString());
        questionnaire.setQuestionnaireTags(Set.of(questionnaireTag));

        QuestionnaireEntity questionnaireEntity = questionnaireMapper.dtoToModel(questionnaire);

        Assertions.assertNotNull(questionnaireEntity.getUuid());
        Assertions.assertNotNull(questionnaireEntity.getQuestionnaireTags().isEmpty());
    }

    @Test
    void dtoToModelUpdate() {

        final ZonedDateTime now = ZonedDateTime.now();

        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setUuid(UUID.randomUUID().toString());

        QuestionnaireTag questionnaireTag = new QuestionnaireTag();
        questionnaireTag.setUuid(UUID.randomUUID().toString());
        questionnaire.setQuestionnaireTags(Set.of(questionnaireTag));

        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setDateModification(now);
        questionnaireEntity.setLastModifiedBy("me");

        questionnaireMapper.dtoToModel(questionnaireEntity, questionnaire);

        Assertions.assertNotNull(questionnaireEntity.getUuid());
        Assertions.assertNotNull(questionnaireEntity.getQuestionnaireTags().isEmpty());
        Assertions.assertTrue(now.equals(questionnaireEntity.getDateModification()));
        Assertions.assertTrue("me".equals(questionnaireEntity.getLastModifiedBy()));

    }

    @Test
    void pageToDto() {
        final ZonedDateTime now = ZonedDateTime.now();
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setDateModification(now);
        questionnaireEntity.setLastModifiedBy("me");

        Page <QuestionnaireEntity> entityPage = new PageImpl <>(List.of(questionnaireEntity));

        Page <Questionnaire> questionnairePage = questionnaireMapper.pageToDto(entityPage);

        Assertions.assertNotNull(questionnairePage);
        Assertions.assertTrue(questionnairePage.getTotalElements() == 1L);

    }
}
