package com.emu.apps.qcm.services.jpa.entity.tags;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class QuestionTagId implements Serializable {

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "tag_id")
    private Long tagId;

    public QuestionTagId() {
    }

    public QuestionTagId(Long entityId, Long tagId) {
        this.questionId = entityId;
        this.tagId = tagId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionTagId that = (QuestionTagId) o;
        return Objects.equals(questionId, that.questionId) &&
                Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, tagId);
    }
}
