package com.emu.apps.qcm.services.jpa.entity.tags;

import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;


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

    public QuestionnaireTag createQuestionnaireTag() {
        return new QuestionnaireTag(questionnaire, tag);
    }
}