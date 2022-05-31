package com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers;

import com.emu.apps.qcm.domain.mappers.CategoryIdMapperImpl;
import com.emu.apps.qcm.domain.mappers.QuestionIdMapperImpl;
import com.emu.apps.qcm.domain.mappers.QuestionnaireIdMapperImpl;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import org.junit.Assert;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {QuestionnaireQuestionEntityMapperImpl.class, CategoryEntityMapperImpl.class, QuestionnaireTagEntityMapperImpl.class, UuidMapperImpl.class,
                QuestionTagEntityMapperImpl.class, QuestionnaireIdMapperImpl.class, CategoryIdMapperImpl.class, QuestionnaireTagEntityMapperImpl.class,
                ResponseEntityMapperImpl.class, QuestionnaireIdMapperImpl.class, QuestionIdMapperImpl.class})
@Tag("MapstructTest")
public class QuestionnaireQuestionEntityMapperTest {

    @Autowired
    private QuestionnaireQuestionEntityMapper questionnaireQuestionEntityMapper;

    @Test
    void questionnaireQuestionEntityToDomain() {


        var questionEntity = new QuestionEntity();
        questionEntity.setNumeroVersion(2);
        questionEntity.setUuid(UUID.randomUUID());

        var questionnaireEntity = new QuestionnaireEntity();
        questionnaireEntity.setUuid(UUID.randomUUID());

        var questionnaireQuestionEntity = new QuestionnaireQuestionEntity(questionnaireEntity, questionEntity, 1);

        var questionnaireQuestion = questionnaireQuestionEntityMapper.questionnaireQuestionEntityToDomain(questionnaireQuestionEntity);


        Assert.assertEquals(2, questionnaireQuestion.getNumeroVersion());

    }

}
