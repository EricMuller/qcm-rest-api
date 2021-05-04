package com.emu.apps.qcm.infra.persistence.mappers;

import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionTags;
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
@ContextConfiguration(classes = {QuestionEntityMapperImpl.class, CategoryEntityMapperImpl.class, UuidMapperImpl.class
        , QuestionTagEntityMapperImpl.class, ResponseEntityMapperImpl.class})
@Tag("MapstructTest")
class QuestionMapperTest {

    static final String QUESTION_TEXT = "Text";

    @Autowired
    private QuestionEntityMapper questionMapper;

    @Test
    void entityToModel() {

        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setUuid(UUID.randomUUID());
        questionEntity.setQuestionText(QUESTION_TEXT);

        Question question = questionMapper.entityToQuestion(questionEntity);


        Assertions.assertNotNull(question);

        Assertions.assertEquals(QUESTION_TEXT, question.getQuestionText());
    }

    @Test
    void entityToQuestionTags(){

        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setUuid(UUID.randomUUID());
        questionEntity.setQuestionText(QUESTION_TEXT);

        QuestionTags questionTags = questionMapper.entityToQuestionTags(questionEntity);


        Assertions.assertNotNull(questionTags);

        Assertions.assertEquals(QUESTION_TEXT, questionTags.getQuestionText());
    }

}
