package com.emu.apps.qcm.services.entity.tags;

import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "questionnaire_tag")
public class QuestionnaireTag implements Serializable {

    @EmbeddedId
    private QuestionnaireTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_id", insertable = false, updatable = false)
    private Questionnaire questionnaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private Tag tag;

    @Column
    private boolean deleted;

    public QuestionnaireTag() {
    }

    public QuestionnaireTag(Questionnaire questionnaire, Tag tag) {
        this.id = new QuestionnaireTagId(questionnaire.getId(), tag.getId());
        this.tag = tag;
        this.questionnaire = questionnaire;
    }

    public QuestionnaireTagId getId() {
        return id;
    }

    public void setId(QuestionnaireTagId id) {
        this.id = id;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }


    public Tag getTag() {
        return tag;
    }


    public void setTag(Tag tag) {
        this.tag = tag;
    }


    public boolean isDeleted() {
        return deleted;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionnaireTag)) return false;
        QuestionnaireTag that = (QuestionnaireTag) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }


}
