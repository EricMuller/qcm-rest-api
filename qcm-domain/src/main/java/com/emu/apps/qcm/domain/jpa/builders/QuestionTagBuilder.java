package com.emu.apps.qcm.domain.jpa.builders;

import com.emu.apps.qcm.domain.entity.questions.Question;
import com.emu.apps.qcm.domain.entity.tags.QuestionTag;
import com.emu.apps.qcm.domain.entity.tags.Tag;


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
