package com.emu.apps.qcm.spi.persistence.mappers;

import com.emu.apps.qcm.api.models.Question;
import com.emu.apps.qcm.api.models.QuestionTag;
import com.emu.apps.qcm.api.models.question.QuestionTags;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {QuestionMapperImpl.class, CategoryMapperImpl.class, UuidMapperImpl.class
        , QuestionTagMapperImpl.class, ResponseMapperImpl.class})
@Tag("MapstructTest")
class QuestionMapperTest {

    static final String QUESTION_TEXT = "Text";

    @Autowired
    private QuestionMapper questionMapper;

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
