package com.emu.apps.qcm.services.entity.questionnaires;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class QuestionnaireQuestionId implements Serializable {

    @Column(name = "questionnaire_id")
    private Long questionnaireId;

    @Column(name = "question_id")
    private Long questionId;

    public QuestionnaireQuestionId() {
    }

    public QuestionnaireQuestionId(Long questionId, Long tagId) {
        this.questionnaireId = questionId;
        this.questionId = tagId;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionnaireQuestionId that = (QuestionnaireQuestionId) o;
        return Objects.equals(questionnaireId, that.questionnaireId) &&
                Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionnaireId, questionId);
    }
}
