package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.domain.ports.ExportService;
import com.emu.apps.qcm.infrastructure.ports.QuestionDOService;
import com.emu.apps.qcm.infrastructure.ports.QuestionnaireDOService;
import com.emu.apps.qcm.mappers.exports.ExportMapper;
import com.emu.apps.qcm.web.dtos.export.ExportDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.emu.apps.qcm.infrastructure.exceptions.EntityExceptionUtil.assertIsPresent;

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
public class ExportServiceAdapter implements ExportService {

    private final QuestionnaireDOService questionnaireDOService;

    private final ExportMapper exportMapper;

    private final QuestionDOService questionDOService;

    public ExportServiceAdapter(QuestionnaireDOService questionnaireDOService, ExportMapper exportMapper, QuestionDOService questionDOService) {
        this.questionnaireDOService = questionnaireDOService;
        this.exportMapper = exportMapper;
        this.questionDOService = questionDOService;
    }

    @Override
    public ExportDto getbyQuestionnaireId(long id) {
        var questionnaire = questionnaireDOService.findOne(id);
        assertIsPresent(questionnaire, String.valueOf(id));
        var questions = questionDOService.findAllWithTagsAndResponseByQuestionnaireId(id);

        var name = generateName(questionnaire);

        return exportMapper.toDto(questionnaire, questions, name);
    }


}
