package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireTagId implements Serializable {

    @Column(name = "questionnaire_id")
    private Long questionnaireId;

    @Column(name = "tag_id")
    private Long tagId;

    public QuestionnaireTagId(Long entityId, Long tagId) {
        this.questionnaireId = entityId;
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionnaireTagId that = (QuestionnaireTagId) o;
        return Objects.equals(questionnaireId, that.questionnaireId) &&
                Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionnaireId, tagId);
    }
}
