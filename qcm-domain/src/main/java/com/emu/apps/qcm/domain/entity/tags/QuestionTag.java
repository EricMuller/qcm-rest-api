package com.emu.apps.qcm.domain.entity.tags;

import com.emu.apps.qcm.domain.entity.questions.Question;
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
public class QuestionTag implements Serializable {

    @EmbeddedId
    private QuestionTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", insertable = false, updatable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private Tag tag;

    public QuestionTag(Question question, Tag tag) {
        this.id = new QuestionTagId(question.getId(), tag.getId());
        this.tag = tag;
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionTag)) return false;
        QuestionTag that = (QuestionTag) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
