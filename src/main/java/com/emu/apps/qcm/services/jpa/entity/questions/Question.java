package com.emu.apps.qcm.services.jpa.entity.questions;


import com.emu.apps.qcm.services.jpa.entity.Status;
import com.emu.apps.qcm.services.jpa.entity.common.AuditableEntity;
import com.emu.apps.qcm.services.jpa.entity.converters.BooleanTFConverter;
import com.emu.apps.qcm.services.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTag;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by eric on 05/06/2017.
 */

@Entity
@NamedEntityGraph(name = "Question.questionTags",
        attributeNodes = {
                @NamedAttributeNode(value = "questionTags", subgraph = "tags")
        },
        subgraphs = @NamedSubgraph(name = "tags", attributeNodes = @NamedAttributeNode("tag")))
@Table(indexes = { @Index(name = "IDX_QTO_CREATE_BY_IDX", columnList = "created_by") })
public class Question extends AuditableEntity<String> {

    @Convert(converter = BooleanTFConverter.class)
    private Boolean mandatory;

    @Column(name = "question", nullable = false, length = 1024)
    private String question;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Response> responses;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Status status = Status.DRAFT;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    private Set<QuestionTag> questionTags = new HashSet<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<QuestionnaireQuestion> questionnaireQuestions = new HashSet<>();

    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return String.format("Question[id=%d, question='%s']", getId(), question);
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Set<QuestionTag> getQuestionTags() {
        return questionTags;
    }

    public void setQuestionTags(Set<QuestionTag> questionTags) {
        this.questionTags = questionTags;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Set<QuestionnaireQuestion> getQuestionnaireQuestions() {
        return questionnaireQuestions;
    }

    public void setQuestionnaireQuestions(Set<QuestionnaireQuestion> questionnaireQuestions) {
        this.questionnaireQuestions = questionnaireQuestions;
    }
}
