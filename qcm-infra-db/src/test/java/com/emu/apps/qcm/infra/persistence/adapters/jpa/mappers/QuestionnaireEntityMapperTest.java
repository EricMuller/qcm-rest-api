package com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers;

import com.emu.apps.qcm.domain.mappers.CategoryIdMapperImpl;
import com.emu.apps.qcm.domain.mappers.QuestionnaireIdMapperImpl;
import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireId;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireTag;
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
@ContextConfiguration(
        classes = {QuestionnaireEntityMapperImpl.class, CategoryEntityMapperImpl.class, QuestionnaireTagEntityMapperImpl.class, UuidMapperImpl.class,
                QuestionnaireIdMapperImpl.class, CategoryIdMapperImpl.class})
@Tag("MapstructTest")
class QuestionnaireEntityMapperTest {

    @Autowired
    private QuestionnaireEntityMapper questionnaireMapper;

    @Test
    void modelToDto() {

        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(UUID.randomUUID());


        Questionnaire questionnaire = questionnaireMapper.modelToDto(questionnaireEntity);

        Assertions.assertNotNull(questionnaire);
    }

    @Test
    void dtoToModel() {

        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(new QuestionnaireId(UUID.randomUUID().toString()));

        QuestionnaireTag questionnaireTag = new QuestionnaireTag();
        questionnaireTag.setUuid(UUID.randomUUID().toString());
        questionnaire.setTags(Set.of(questionnaireTag));

        QuestionnaireEntity questionnaireEntity = questionnaireMapper.dtoToModel(questionnaire);

        Assertions.assertNotNull(questionnaireEntity.getTags());
    }

    @Test
    void dtoToModelUpdate() {

        final ZonedDateTime now = ZonedDateTime.now();

        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(new QuestionnaireId(UUID.randomUUID().toString()));

        QuestionnaireTag questionnaireTag = new QuestionnaireTag();
        questionnaireTag.setUuid(UUID.randomUUID().toString());
        questionnaire.setTags(Set.of(questionnaireTag));

        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(UUID.randomUUID());
        questionnaireEntity.setDateModification(now);
        questionnaireEntity.setLastModifiedBy("me");

        questionnaireMapper.dtoToModel(questionnaire, questionnaireEntity);

        Assertions.assertNotNull(questionnaireEntity.getUuid());
        Assertions.assertNotNull(questionnaireEntity.getTags());
        Assertions.assertEquals(now, questionnaireEntity.getDateModification());
        Assertions.assertEquals("me", questionnaireEntity.getLastModifiedBy());

    }

    @Test
    void pageToDto() {
        final ZonedDateTime now = ZonedDateTime.now();
        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(UUID.randomUUID());
        questionnaireEntity.setDateModification(now);
        questionnaireEntity.setLastModifiedBy("me");

        Page <QuestionnaireEntity> entityPage = new PageImpl <>(List.of(questionnaireEntity));

        Page <Questionnaire> questionnairePage = questionnaireMapper.pageToDto(entityPage);

        Assertions.assertNotNull(questionnairePage);
        Assertions.assertEquals(1L, questionnairePage.getTotalElements());

    }
}
