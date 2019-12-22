package com.emu.apps.qcm.services.jpa.entity.questionnaires;

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
public class QuestionnaireQuestionId implements Serializable {

    @Column(name = "questionnaire_id")
    private Long questionnaireId;

    @Column(name = "question_id")
    private Long questionId;

    public QuestionnaireQuestionId(Long questionId, Long tagId) {
        this.questionnaireId = questionId;
        this.questionId = tagId;
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
