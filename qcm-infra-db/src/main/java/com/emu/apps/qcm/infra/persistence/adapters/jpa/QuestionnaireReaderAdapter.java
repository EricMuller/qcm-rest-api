package com.emu.apps.qcm.infra.persistence.adapters.jpa;


import com.emu.apps.qcm.domain.model.questionnaire.Suggest;
import com.emu.apps.qcm.infra.persistence.QuestionnaireReaderPort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.QuestionnaireRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.SuggestEntityMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class QuestionnaireReaderAdapter implements QuestionnaireReaderPort {

    private final QuestionnaireRepository questionnaireRepository;

    private final SuggestEntityMapper suggestMapper;


    public QuestionnaireReaderAdapter(QuestionnaireRepository questionnaireRepository, SuggestEntityMapper suggestMapper) {
        this.questionnaireRepository = questionnaireRepository;

        this.suggestMapper = suggestMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable <Suggest> findByTitleContaining(String queryText) {
        final List <Suggest> suggestions = new ArrayList <>();
        if (StringUtils.isNoneEmpty(queryText)) {
            suggestMapper.modelsToSuggestDtos(questionnaireRepository.findByTitleContaining(queryText)).forEach(suggestions::add);
            //todo: tagMapper.modelsToSugestDtos(tagService.findByLibelleContaining(queryText)).forEach(suggestions::add);
        }
        return suggestions;
    }


}
