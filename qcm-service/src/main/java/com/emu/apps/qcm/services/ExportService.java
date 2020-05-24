package com.emu.apps.qcm.services;

import com.emu.apps.qcm.domain.QuestionDOService;
import com.emu.apps.qcm.domain.QuestionnaireDOService;
import com.emu.apps.qcm.domain.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.mappers.exports.ExportMapper;
import com.emu.apps.qcm.web.dtos.export.ExportDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.emu.apps.qcm.domain.exceptions.EntityExceptionUtil.assertIsPresent;

/**
 * Questionnaire Business Delegate
 * <p>
 * <ul>
 * <li>create a new questionnaire</li>
 * <li>delete a questionnaire</li>
 * <li>update a questionnaire</li>
 * <li>add a question to a questionnaire</li>
 * </ul>
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
@Transactional
public class ExportService {

    private final QuestionnaireDOService questionnaireDOService;

    private final ExportMapper exportMapper;

    private final QuestionDOService questionDOService;

    public ExportService(QuestionnaireDOService questionnaireDOService, ExportMapper exportMapper, QuestionDOService questionDOService) {
        this.questionnaireDOService = questionnaireDOService;
        this.exportMapper = exportMapper;
        this.questionDOService = questionDOService;
    }

    public ExportDto getbyQuestionnaireId(long id) {
        var questionnaire = questionnaireDOService.findOne(id);
        assertIsPresent(questionnaire, String.valueOf(id));
        var questions = questionDOService.findAllWithTagsAndResponseByQuestionnaireId(id);

        var name = generateName(questionnaire);

        return exportMapper.toDto(questionnaire, questions, name);
    }

    private String generateName(Questionnaire questionnaire) {

        return questionnaire.getId() + "-" + questionnaire.getTitle();

    }


}
