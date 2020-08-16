package com.emu.apps.qcm.spi.persistence.adapters.jpa.builders;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionnaireTagEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.Tag;


public class QuestionnaireTagBuilder {

    private QuestionnaireEntity questionnaire;

    private Tag tag;

    public QuestionnaireTagBuilder setQuestionnaire(QuestionnaireEntity questionnaire) {
        this.questionnaire = questionnaire;
        return this;
    }

    public QuestionnaireTagBuilder setTag(Tag tag) {
        this.tag = tag;
        return this;
    }

    public QuestionnaireTagEntity build() {
        return new QuestionnaireTagEntity(questionnaire, tag);
    }
}
