package com.emu.apps.qcm.services.entity.questionnaires;

import com.emu.apps.qcm.services.entity.Status;
import com.emu.apps.qcm.services.entity.common.AuditableEntity;
import com.emu.apps.qcm.services.entity.epics.Category;
import com.emu.apps.qcm.services.entity.tags.QuestionnaireTag;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

@NamedEntityGraphs({
        @NamedEntityGraph(name = "Questionnaire.questionnaireTags",
                attributeNodes = {
                        @NamedAttributeNode(value = "questionnaireTags", subgraph = "tags")
                },
                subgraphs = @NamedSubgraph(name = "tags", attributeNodes = @NamedAttributeNode("tag")))
})
@Table(indexes = { @Index(name = "IDX_QTE_CREATE_BY_IDX", columnList = "created_by") })
public class Questionnaire extends AuditableEntity<String> {

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "LOCALE")
    private String locale;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<QuestionnaireQuestion> questionnaireQuestions = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 20)
    private Set<QuestionnaireTag> questionnaireTags = new HashSet<>();

    public Questionnaire() {
    }

    public Questionnaire(String title) {
        this.title = title;
    }

    public Set<QuestionnaireQuestion> getQuestionnaireQuestions() {
        return questionnaireQuestions;
    }

    public void setQuestionnaireQuestions(Set<QuestionnaireQuestion> questionnaireQuestions) {
        this.questionnaireQuestions = questionnaireQuestions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Set<QuestionnaireTag> getQuestionnaireTags() {
        return questionnaireTags;
    }

    public void setQuestionnaireTags(Set<QuestionnaireTag> questionnaireTags) {
        this.questionnaireTags = questionnaireTags;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
