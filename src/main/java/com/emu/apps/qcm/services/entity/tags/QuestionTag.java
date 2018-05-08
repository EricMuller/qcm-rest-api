package com.emu.apps.qcm.services.entity.tags;

import com.emu.apps.qcm.services.entity.questions.Question;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "question_tag")
public class QuestionTag implements Serializable {

    @EmbeddedId
    private QuestionTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", insertable = false, updatable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private Tag tag;

    public QuestionTag() {
    }

    public QuestionTag(Question question, Tag tag) {
        this.id = new QuestionTagId(question.getId(), tag.getId());
        this.tag = tag;
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


    public QuestionTagId getId() {
        return id;
    }

    public void setId(QuestionTagId id) {
        this.id = id;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionTag)) return false;
        QuestionTag that = (QuestionTag) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
