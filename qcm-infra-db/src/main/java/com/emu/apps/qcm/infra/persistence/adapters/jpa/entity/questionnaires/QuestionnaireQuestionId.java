package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires;

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

    @Column(name = "QUESTIONNAIRE_ID")
    private Long questionnaireId;

    @Column(name = "QUESTION_ID")
    private Long questionId;

    @Column(name = "NUMERO_VERSION")
    private int numeroVersion;

    public QuestionnaireQuestionId(Long questionnaireId, Long questionId,int numeroVersion) {
        this.questionnaireId = questionnaireId;
        this.questionId = questionId;
        this.numeroVersion = numeroVersion;
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
