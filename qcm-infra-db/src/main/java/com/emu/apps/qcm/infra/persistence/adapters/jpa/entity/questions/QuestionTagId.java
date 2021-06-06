package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class QuestionTagId implements Serializable {

    @Column(name = "QUESTION_ID")
    private Long questionId;

    @Column(name = "TAG_ID")
    private Long tagId;

    public QuestionTagId(Long entityId, Long tagId) {
        this.questionId = entityId;
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
