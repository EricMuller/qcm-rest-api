package com.emu.apps.qcm.infra.persistence.adapters.jpa.builders;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionTagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.TagQuestionEntity;


public class QuestionTagBuilder {

    private QuestionEntity question;

    private TagQuestionEntity tag;

    public QuestionTagBuilder setQuestion(QuestionEntity question) {
        this.question = question;
        return this;
    }

    public QuestionTagBuilder setTag(TagQuestionEntity tag) {
        this.tag = tag;
        return this;
    }

    public QuestionTagEntity build() {
        return new QuestionTagEntity(question, tag);
    }
}
