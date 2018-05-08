package com.emu.apps.qcm.services.entity.questionnaires;

import com.emu.apps.qcm.services.entity.common.BasicEntity;
import com.emu.apps.qcm.services.entity.epics.Epic;
import com.emu.apps.qcm.services.entity.tags.QuestionnaireTag;

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
public class Questionnaire extends BasicEntity {

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "LOCALE")
    private String locale;

//    @Column(name = "POSITION")
//    private Long position;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<QuestionnaireQuestion> questionnaireQuestions = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Epic epic;

    @OneToMany(mappedBy = "questionnaire")
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

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
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
}
