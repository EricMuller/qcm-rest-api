package com.emu.apps.qcm.infra.persistence.mappers;

import com.emu.apps.qcm.domain.dtos.published.PushishedQuestionnaireQuestionDto;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PublishedMapperImpl.class,  UuidMapperImpl.class})
@Tag("MapstructTest")
class PublishedMapperTest {

    @Autowired
    private PublishedMapper publishedMapper;

    @Test
    void toPublishedQuestionnaireQuestionDto() {

        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setUuid(UUID.randomUUID());
        questionEntity.setQuestionText("Text");

        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setUuid(UUID.randomUUID());

        QuestionnaireQuestionEntity questionnaireQuestionEntity = new QuestionnaireQuestionEntity();
        questionnaireQuestionEntity.setQuestion(questionEntity);
        questionnaireQuestionEntity.setQuestionnaire(questionnaireEntity);

        PushishedQuestionnaireQuestionDto pushishedQuestionnaireQuestionDto = publishedMapper.toPublishedQuestionnaireQuestionDto(questionnaireQuestionEntity);

        Assertions.assertNotNull(pushishedQuestionnaireQuestionDto);

        Assertions.assertEquals("Text",pushishedQuestionnaireQuestionDto.getQuestionText());
    }


}
