package com.emu.apps.qcm.services.jpa.entity.tags;

import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "questionnaire_tag")
@Getter
@Setter
@NoArgsConstructor
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


    public QuestionnaireTag(Questionnaire questionnaire, Tag tag) {
        this.id = new QuestionnaireTagId(questionnaire.getId(), tag.getId());
        this.tag = tag;
        this.questionnaire = questionnaire;
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
