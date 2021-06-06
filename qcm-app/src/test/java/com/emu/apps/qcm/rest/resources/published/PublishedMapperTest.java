package com.emu.apps.qcm.rest.resources.published;

import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.infra.persistence.adapters.mappers.UuidMapperImpl;
import com.emu.apps.qcm.rest.mappers.PublishedMapper;
import com.emu.apps.qcm.rest.mappers.PublishedMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PublishedMapperImpl.class,  UuidMapperImpl.class})
@Tag("MapstructTest")
class PublishedMapperTest {

    @Autowired
    private PublishedMapper publishedMapper;

    @Test
    void toPublishedQuestionnaireQuestionDto() {

        QuestionnaireQuestion questionnaireQuestion = new QuestionnaireQuestion();
        questionnaireQuestion.setQuestion("Text");


        PushishedQuestionnaireQuestion pushishedQuestionnaireQuestionDto = publishedMapper.toPublishedQuestionnaireQuestion(questionnaireQuestion);

        Assertions.assertNotNull(pushishedQuestionnaireQuestionDto);

        Assertions.assertEquals("Text",pushishedQuestionnaireQuestionDto.getQuestionText());
    }


}
