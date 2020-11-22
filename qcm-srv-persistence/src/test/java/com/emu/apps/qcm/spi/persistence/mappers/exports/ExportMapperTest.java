package com.emu.apps.qcm.spi.persistence.mappers.exports;

import com.emu.apps.qcm.domain.dtos.export.v1.QuestionExportDto;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.spi.persistence.mappers.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ExportMapperImpl.class, CategoryMapperImpl.class, QuestionnaireTagMapperImpl.class, UuidMapperImpl.class})
@Tag("MapstructTest")
class ExportMapperTest {

    @Autowired
    private ExportMapper exportMapper;

    @Test
    void entityToQuestionExportDto() {

        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setUuid(UUID.randomUUID());
        questionEntity.setQuestionText("Text");


        QuestionnaireQuestionEntity entityToQuestionExportDto = new QuestionnaireQuestionEntity();
        entityToQuestionExportDto.setQuestion(questionEntity);

        QuestionExportDto questionExportDto = exportMapper.entityToQuestionExportDto(entityToQuestionExportDto);


        Assertions.assertNotNull(questionExportDto);

        Assertions.assertEquals("Text",questionExportDto.getQuestionText());
    }


}
