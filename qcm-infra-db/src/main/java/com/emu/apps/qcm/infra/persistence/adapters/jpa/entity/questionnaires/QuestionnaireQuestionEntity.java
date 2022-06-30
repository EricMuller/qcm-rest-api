package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
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
                //  @NamedAttributeNode(value = "question")
                @NamedAttributeNode(value = "question", subgraph = "QuestionnaireQuestion.question")
        },
        subgraphs = {
//                @NamedSubgraph(name = "question", attributeNodes =@NamedAttributeNode(value = "questionTags", subgraph = "tags")),
                // @NamedSubgraph(name = "responses", attributeNodes =@NamedAttributeNode(value = "questionTags", subgraph = "tags")),
                @NamedSubgraph(name = "QuestionnaireQuestion.question",
                        attributeNodes = {
                                @NamedAttributeNode("mpttCategory"),

                        }
                )

        }
)

@Table(name = "QUESTIONNAIRE_QUESTION")
@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireQuestionEntity implements Serializable {

    @EmbeddedId
    private QuestionnaireQuestionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTIONNAIRE_ID", insertable = false, updatable = false)
    private QuestionnaireEntity questionnaire;

    //insertable = false , You would do that when the responsibility of creating/updating the referenced column isn't in the current entity, but in another entity.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID", insertable = false, updatable = false, nullable = false, referencedColumnName="ID")
    @JoinColumn(name = "NUMERO_VERSION", insertable = false, updatable = false, nullable = false, referencedColumnName="NUMERO_VERSION")
    private QuestionEntity question;

    @Column
    private boolean deleted;

    @Column(name = "POSITION", nullable = false)
    private Integer position;

    @Column(name = "POINTS", nullable = false)
    private Integer points = 1;

    public QuestionnaireQuestionEntity(@NotNull QuestionnaireEntity questionnaire, @NotNull QuestionEntity question, Integer position) {
        this.id = new QuestionnaireQuestionId(questionnaire.getId(), question.getId(), question.getNumeroVersion());
        this.questionnaire = questionnaire;
        this.question = question;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionnaireQuestionEntity that = (QuestionnaireQuestionEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
