package com.emu.apps.qcm.infra.persistence.adapters.mappers;

import com.emu.apps.qcm.domain.mappers.CategoryIdMapperImpl;
import com.emu.apps.qcm.domain.mappers.QuestionIdMapperImpl;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionWithTagsOnly;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.AccountEntity;
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
        , QuestionTagEntityMapperImpl.class, ResponseEntityMapperImpl.class, QuestionIdMapperImpl.class, CategoryIdMapperImpl.class})
@Tag("MapstructTest")
class QuestionEntityMapperTest {

    static final String QUESTION_TEXT = "Text";

    private static final String USER_NAME = "userName";

    @Autowired
    private QuestionEntityMapper questionMapper;

    @Test
    void entityToModel() {

        QuestionEntity questionEntity = new QuestionEntity(UUID.randomUUID());

        AccountEntity accountEntity = new AccountEntity(UUID.randomUUID());
        accountEntity.setUserName(USER_NAME);
        questionEntity.setCreatedBy(accountEntity.getId().toString());
        questionEntity.setLastModifiedBy(accountEntity.getId().toString());

        questionEntity.setQuestionText(QUESTION_TEXT);

        Question question = questionMapper.entityToQuestion(questionEntity);

        Assertions.assertNotNull(question);

        Assertions.assertEquals(QUESTION_TEXT, question.getQuestionText());
        Assertions.assertEquals(accountEntity.getId().toString(), question.getCreatedBy());
        Assertions.assertEquals(accountEntity.getId().toString(), question.getLastModifiedBy());
    }

    @Test
    void entityToQuestionTags() {

        QuestionEntity questionEntity = new QuestionEntity(UUID.randomUUID());

        AccountEntity accountEntity = new AccountEntity(UUID.randomUUID());
        accountEntity.setUserName(USER_NAME);
        questionEntity.setCreatedBy(accountEntity.getId().toString());
        questionEntity.setLastModifiedBy(accountEntity.getId().toString());

        questionEntity.setQuestionText(QUESTION_TEXT);

        QuestionWithTagsOnly questionWithTagsOnly = questionMapper.entityToQuestionWithTagsOnly(questionEntity);

        Assertions.assertNotNull(questionWithTagsOnly);

        Assertions.assertEquals(QUESTION_TEXT, questionWithTagsOnly.getQuestionText());
        Assertions.assertEquals(accountEntity.getId().toString(), questionWithTagsOnly.getCreatedBy());
        Assertions.assertEquals(accountEntity.getId().toString(), questionWithTagsOnly.getLastModifiedBy());
    }

}
