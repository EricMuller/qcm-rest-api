package com.emu.apps.qcm.infra.persistence.mappers.exports;


import com.emu.apps.qcm.domain.model.questionnaire.QuestionnaireQuestion;
import com.emu.apps.qcm.infra.persistence.mappers.CategoryEntityMapperImpl;
import com.emu.apps.qcm.infra.persistence.mappers.QuestionnaireTagEntityMapperImpl;
import com.emu.apps.qcm.infra.persistence.mappers.UuidMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;


//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {ExportMapperImpl.class, CategoryMapperImpl.class, QuestionnaireTagMapperImpl.class, UuidMapperImpl.class})
//@Tag("MapstructTest")
//class ExportMapperTest {
//
//    @Autowired
//    private ExportMapper exportMapper;
//
//    @Test
//    void entityToQuestionExportDto() {
//
//        String uuid = UUID.randomUUID().toString();
//        QuestionnaireQuestion questionnaireQuestion = new QuestionnaireQuestion();
//        questionnaireQuestion.setQuestion("Text");
//        questionnaireQuestion.setUuid(uuid);
//
//        QuestionExport questionExportDto = exportMapper.entityToQuestionExportDto(questionnaireQuestion);
//
//
//        Assertions.assertNotNull(questionExportDto);
//
//        Assertions.assertEquals("Text", questionExportDto.getQuestionText());
//
//    }
//
//
//}