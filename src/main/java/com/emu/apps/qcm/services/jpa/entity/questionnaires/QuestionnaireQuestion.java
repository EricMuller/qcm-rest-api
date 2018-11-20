package com.emu.apps.qcm.services.jpa.entity.questionnaires;

import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "questionnaire_question")
@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireQuestion implements Serializable {

    @EmbeddedId
    private QuestionnaireQuestionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_id", insertable = false, updatable = false)
    private Questionnaire questionnaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", insertable = false, updatable = false)
    private Question question;

    @Column
    private boolean deleted;

    @Column(name = "POSITION", nullable = false)
    private Long position;

    public QuestionnaireQuestion(Questionnaire questionnaire, Question question, Long position) {
        this.id = new QuestionnaireQuestionId(questionnaire.getId(), question.getId());
        this.questionnaire = questionnaire;
        this.question = question;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionnaireQuestion that = (QuestionnaireQuestion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
