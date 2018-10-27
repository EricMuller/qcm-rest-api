package com.emu.apps.qcm.services.jpa.entity.questionnaires;

import com.emu.apps.qcm.services.jpa.entity.questions.Question;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "questionnaire_question")
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

    public QuestionnaireQuestion() {
    }

    public QuestionnaireQuestion(Questionnaire questionnaire, Question question, Long position) {
        this.id = new QuestionnaireQuestionId(questionnaire.getId(), question.getId());
        this.questionnaire = questionnaire;
        this.question = question;
        this.position = position;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public QuestionnaireQuestionId getId() {
        return id;
    }

    public void setId(QuestionnaireQuestionId id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
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
