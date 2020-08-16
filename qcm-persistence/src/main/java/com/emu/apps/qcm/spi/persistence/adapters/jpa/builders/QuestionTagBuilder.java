package com.emu.apps.qcm.spi.persistence.adapters.jpa.builders;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionTagEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.Tag;


public class QuestionTagBuilder {

    private QuestionEntity question;

    private Tag tag;

    public QuestionTagBuilder setQuestion(QuestionEntity question) {
        this.question = question;
        return this;
    }

    public QuestionTagBuilder setTag(Tag tag) {
        this.tag = tag;
        return this;
    }

    public QuestionTagEntity build() {
        return new QuestionTagEntity(question, tag);
    }
}
