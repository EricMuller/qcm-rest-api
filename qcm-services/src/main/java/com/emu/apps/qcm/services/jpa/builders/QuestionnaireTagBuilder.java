package com.emu.apps.qcm.services.jpa.builders;

import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.services.entity.tags.Tag;


public class QuestionnaireTagBuilder {

    private Questionnaire questionnaire;

    private Tag tag;

    public QuestionnaireTagBuilder setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
        return this;
    }

    public QuestionnaireTagBuilder setTag(Tag tag) {
        this.tag = tag;
        return this;
    }

    public QuestionnaireTag build() {
        return new QuestionnaireTag(questionnaire, tag);
    }
}
