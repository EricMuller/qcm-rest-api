package com.emu.apps.qcm.infra.persistence.adapters.mappers.exports;


import com.emu.apps.qcm.infra.persistence.adapters.mappers.CategoryEntityMapperImpl;
import com.emu.apps.qcm.infra.persistence.adapters.mappers.QuestionnaireTagEntityMapperImpl;
import com.emu.apps.qcm.infra.persistence.adapters.mappers.UuidMapperImpl;


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
