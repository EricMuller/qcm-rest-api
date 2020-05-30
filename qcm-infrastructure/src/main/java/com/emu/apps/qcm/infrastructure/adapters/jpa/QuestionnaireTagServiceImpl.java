package com.emu.apps.qcm.infrastructure.adapters.jpa;


import com.emu.apps.qcm.infrastructure.ports.QuestionnaireTagDOService;
import com.emu.apps.qcm.infrastructure.ports.TagDOService;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.Tag;
import com.emu.apps.qcm.infrastructure.exceptions.EntityExceptionUtil;
import com.emu.apps.qcm.infrastructure.adapters.jpa.builders.QuestionnaireTagBuilder;
import com.emu.apps.qcm.infrastructure.adapters.jpa.repositories.QuestionnaireRepository;
import com.emu.apps.qcm.infrastructure.adapters.jpa.repositories.QuestionnaireTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Objects;

@Service
@Transactional
public class QuestionnaireTagServiceImpl implements QuestionnaireTagDOService {

    private final QuestionnaireTagRepository questionnaireTagRepository;

    private final QuestionnaireRepository questionnaireRepository;

    private final TagDOService tagService;

    @Autowired
    public QuestionnaireTagServiceImpl(QuestionnaireTagRepository questionnaireTagRepository, QuestionnaireRepository questionnaireRepository, TagDOService tagService) {
        this.questionnaireTagRepository = questionnaireTagRepository;
        this.questionnaireRepository = questionnaireRepository;
        this.tagService = tagService;
    }

    @Override
    public QuestionnaireTag saveQuestionnaireTag(QuestionnaireTag questionnaireTag) {
        return questionnaireTagRepository.save(questionnaireTag);
    }

    @Transactional()
    public Questionnaire saveQuestionnaireTags(long questionnaireId, Iterable <QuestionnaireTag> questionnaireTags, Principal principal) {

        var questionnaire = questionnaireRepository.findById(questionnaireId).orElse(null);

        EntityExceptionUtil.assertIsPresent(questionnaire, "Questionnaire not found");

        if (Objects.nonNull(questionnaire)) {
            questionnaire.getQuestionnaireTags().clear();

            if (Objects.nonNull(questionnaireTags)) {
                for (QuestionnaireTag questionnaireTag : questionnaireTags) {
                    Tag tag;
                    if (Objects.nonNull(questionnaireTag.getId().getTagId())) {
                        tag = tagService.findById(questionnaireTag.getId().getTagId()).orElse(null);
                    } else {
                        tag = tagService.findOrCreateByLibelle(questionnaireTag.getTag().getLibelle(), principal);
                    }
                    if (tag != null) {
                        QuestionnaireTag newTag = new QuestionnaireTagBuilder().setQuestionnaire(questionnaire).setTag(tag).build();
                        questionnaire.getQuestionnaireTags().add(questionnaireTagRepository.save(newTag));
                    }
                }
            }
        }
        return questionnaire;
    }

}
