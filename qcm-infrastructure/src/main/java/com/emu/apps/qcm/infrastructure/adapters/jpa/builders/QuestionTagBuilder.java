package com.emu.apps.qcm.infrastructure.adapters.jpa.builders;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Question;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.QuestionTag;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.tags.Tag;


public class QuestionTagBuilder {

    private Question question;

    private Tag tag;

    public QuestionTagBuilder setQuestion(Question question) {
        this.question = question;
        return this;
    }

    public QuestionTagBuilder setTag(Tag tag) {
        this.tag = tag;
        return this;
    }

    public QuestionTag build() {
        return new QuestionTag(question, tag);
    }
}
