package com.emu.apps.qcm.services.entity.tags;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class QuestionnaireTagId implements Serializable {

    @Column(name = "questionnaire_id")
    private Long questionnaireId;

    @Column(name = "tag_id")
    private Long tagId;

    public QuestionnaireTagId() {
    }

    public QuestionnaireTagId(Long entityId, Long tagId) {
        this.questionnaireId = entityId;
        this.tagId = tagId;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
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
        QuestionnaireTagId that = (QuestionnaireTagId) o;
        return Objects.equals(questionnaireId, that.questionnaireId) &&
                Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionnaireId, tagId);
    }
}
