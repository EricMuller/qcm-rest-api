package com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NamedEntityGraph(name = "QuestionnaireQuestion.question",
        attributeNodes = {
                @NamedAttributeNode(value = "question")
//                @NamedAttributeNode(value = "question", subgraph = "tags")
        },
        subgraphs = {
//                @NamedSubgraph(name = "question", attributeNodes =@NamedAttributeNode(value = "questionTags", subgraph = "tags")),
                @NamedSubgraph(name = "tags", attributeNodes = @NamedAttributeNode("tag"))
                }
        )

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

    public QuestionnaireQuestion(@NotNull Questionnaire questionnaire, @NotNull Question question, Long position) {
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
