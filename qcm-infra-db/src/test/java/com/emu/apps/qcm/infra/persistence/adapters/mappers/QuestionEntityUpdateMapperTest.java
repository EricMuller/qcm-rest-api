package com.emu.apps.qcm.infra.persistence.adapters.mappers;

import com.emu.apps.qcm.domain.mappers.AccountIdMapperImpl;
import com.emu.apps.qcm.domain.mappers.CategoryIdMapperImpl;
import com.emu.apps.qcm.domain.mappers.QuestionIdMapperImpl;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.Response;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.AccountEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.ResponseEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {QuestionEntityUpdateMapperImpl.class, CategoryEntityMapperImpl.class, UuidMapperImpl.class
        , QuestionTagEntityMapperImpl.class, ResponseEntityMapperImpl.class, QuestionIdMapperImpl.class, CategoryIdMapperImpl.class,
        AccountIdMapperImpl.class, AccountEntityMapperImpl.class})
@Tag("MapstructTest")
class QuestionEntityUpdateMapperTest {

    static final String QUESTION_TEXT = "Text";

    static final String QUESTION_TEXT2 = "Text2";

    private static final String USER_NAME = "userName";

    @Autowired
    private QuestionEntityUpdateMapper questionMapper;

    @Test
    void testQuestionToEntity() {


        var questionEntity = new QuestionEntity(UUID.randomUUID());

        var accountEntity = new AccountEntity(UUID.randomUUID());
        accountEntity.setUserName(USER_NAME);

        questionEntity.setOwner(accountEntity);
        questionEntity.setCreatedBy(accountEntity.getUuid().toString());
        questionEntity.setLastModifiedBy(accountEntity.getUuid().toString());
        questionEntity.setQuestionText(QUESTION_TEXT);


        var question = new Question();
        question.setQuestionText(QUESTION_TEXT2);

        var response = new Response();
        response.setGood(false);

        question.setResponses(asList(response));

        var questionUpdated = questionMapper.questionToEntity(question, questionEntity);


        Assertions.assertNotNull(questionUpdated);

        Assertions.assertEquals(QUESTION_TEXT2, questionUpdated.getQuestionText());
        Assertions.assertEquals(accountEntity.getUuid().toString(), questionUpdated.getCreatedBy());
        Assertions.assertEquals(accountEntity.getUuid().toString(), questionUpdated.getLastModifiedBy());

        Assertions.assertNotNull(questionUpdated.getOwner());
        Assertions.assertNotNull(USER_NAME, questionUpdated.getOwner().getUserName());

        Assertions.assertNull(questionUpdated.getResponses());

    }

    @Test
    void responsesToEntities() {
        UUID uuid = UUID.randomUUID();

        var response = new Response();
        response.setGood(false);
        response.setUuid(uuid.toString());

        var responses = asList(response);


        var responseEntity = new ResponseEntity();
        responseEntity.setGood(true);
        responseEntity.setUuid(uuid);

        List <ResponseEntity> responsesEntities = new ArrayList <>(asList(responseEntity));

        var responsesEntitiesUpdates = questionMapper.responsesToEntities(responses, responsesEntities);


        Assertions.assertNotNull(responsesEntitiesUpdates);
        Assertions.assertEquals(1 ,responsesEntitiesUpdates.size());


        responsesEntitiesUpdates.stream()
                .findFirst()
                .ifPresent(responseEntity1 -> Assertions.assertFalse(responseEntity1.getGood()));


    }


}
