package com.emu.apps.qcm.rest.mappers;

import com.emu.apps.qcm.domain.mappers.AccountIdMapperImpl;
import com.emu.apps.qcm.domain.mappers.CategoryIdMapperImpl;
import com.emu.apps.qcm.domain.mappers.QuestionIdMapperImpl;
import com.emu.apps.qcm.domain.mappers.QuestionnaireIdMapperImpl;
import com.emu.apps.qcm.domain.mappers.TagIdMapperImpl;
import com.emu.apps.qcm.domain.mappers.WebhookIdMapperImpl;
import com.emu.apps.qcm.domain.model.question.Question;
import com.emu.apps.qcm.domain.model.question.QuestionId;
import com.emu.apps.qcm.domain.model.question.QuestionWithTagsOnly;
import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.rest.controllers.management.resources.AccountResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {QcmResourceMapperImpl.class, QuestionIdMapperImpl.class,
        QuestionnaireIdMapperImpl.class, AccountIdMapperImpl.class, CategoryIdMapperImpl.class,
        WebhookIdMapperImpl.class, TagIdMapperImpl.class})
@Tag("MapstructTest")
class QcmResourceMapperTest {


    @Autowired
    private QcmResourceMapper qcmResourceMapper;

    @Test
    void testAccountToModel() {

        var uuid = UUID.randomUUID().toString();
        var resource = new AccountResource();
        resource.setUuid(uuid);

        var account = qcmResourceMapper.accountResourceToModel(resource);

        Assertions.assertNotNull(account);

        Assertions.assertNotNull(account.getId(), "uuid should not be Null");

        assertEquals(uuid, account.getId().toUuid());

    }

    @Test
    void testquestionToQuestionResource() {

        var uuid = UUID.randomUUID().toString();
        var question = new Question();
        question.setNumeroVersion(2);
        question.setId(new QuestionId(uuid));

        var questionResource = qcmResourceMapper.questionToQuestionResource(question);

        Assertions.assertNotNull(questionResource);

        assertEquals(2, questionResource.getNumeroVersion());


    }

    @Test
    void testquestionTagsToSearchResources() {

        var uuid = UUID.randomUUID().toString();

        var questionWithTagsOnly = new QuestionWithTagsOnly();
        questionWithTagsOnly.setNumeroVersion(2);
        questionWithTagsOnly.setId(new QuestionId(uuid));

        var searchQuestionResource = qcmResourceMapper.questionTagsToSearchResources(questionWithTagsOnly);

        assertEquals(2, searchQuestionResource.getNumeroVersion());


    }

    @Test
    void testquestionnaireQuestionToResources() {

        var questionnaireUuid = UUID.randomUUID().toString();
        var questionUuid = UUID.randomUUID().toString();

        var questionnaireQuestion = new QuestionnaireQuestion();
        questionnaireQuestion.setNumeroVersion(2);
        questionnaireQuestion.setId(new QuestionId(questionUuid));
        var questionnaireQuestionResource = qcmResourceMapper.questionnaireQuestionToResources(questionnaireQuestion, questionnaireUuid);

        Assertions.assertAll(
                () -> assertEquals(2, questionnaireQuestionResource.getNumeroVersion()),
                () -> assertEquals(questionnaireUuid, questionnaireQuestionResource.getQuestionnaireUuid()),
                () -> assertEquals(questionUuid, questionnaireQuestionResource.getUuid()));


    }

}
