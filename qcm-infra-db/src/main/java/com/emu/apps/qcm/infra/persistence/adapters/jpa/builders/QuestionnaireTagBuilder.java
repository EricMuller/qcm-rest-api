package com.emu.apps.qcm.infra.persistence.adapters.jpa.builders;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireTagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.tags.TagEntity;


public class QuestionnaireTagBuilder {

    private QuestionnaireEntity questionnaire;

    private TagEntity tag;

    public QuestionnaireTagBuilder setQuestionnaire(QuestionnaireEntity questionnaire) {
        this.questionnaire = questionnaire;
        return this;
    }

    public QuestionnaireTagBuilder setTag(TagEntity tag) {
        this.tag = tag;
        return this;
    }

    public QuestionnaireTagEntity build() {
        return new QuestionnaireTagEntity(questionnaire, tag);
    }
}
