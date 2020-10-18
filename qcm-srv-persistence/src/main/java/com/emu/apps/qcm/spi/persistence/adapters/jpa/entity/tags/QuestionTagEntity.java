package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions.QuestionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "question_tag")
@Getter
@Setter
@NoArgsConstructor
public class QuestionTagEntity implements Serializable {

    @EmbeddedId
    private QuestionTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", insertable = false, updatable = false)
    private QuestionEntity question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private TagEntity tag;

    public QuestionTagEntity(QuestionEntity question, TagEntity tag) {
        this.id = new QuestionTagId(question.getId(), tag.getId());
        this.tag = tag;
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionTagEntity)) return false;
        QuestionTagEntity that = (QuestionTagEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
