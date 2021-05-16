package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.tags;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "QUESTIONNAIRE_TAG")
@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireTagEntity implements Serializable {

    @EmbeddedId
    private QuestionnaireTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTIONNAIRE_ID", insertable = false, updatable = false)
    private QuestionnaireEntity questionnaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID", insertable = false, updatable = false)
    private TagEntity tag;

    @Column(name = "DELETED")
    private boolean deleted;


    public QuestionnaireTagEntity(QuestionnaireEntity questionnaire, TagEntity tag) {
        this.id = new QuestionnaireTagId(questionnaire.getId(), tag.getId());
        this.tag = tag;
        this.questionnaire = questionnaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionnaireTagEntity)) return false;
        QuestionnaireTagEntity that = (QuestionnaireTagEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
