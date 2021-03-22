package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.tags;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.AuditableEntity.BaseSpecification;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;

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

    public static final class SpecificationBuilder extends BaseSpecification <QuestionTagEntity> {

        private String letter;

        private String principal;

        public SpecificationBuilder() {
            //nope
        }

        public QuestionTagEntity.SpecificationBuilder setPrincipal(String principal) {
            this.principal = principal;
            return this;
        }

        public QuestionTagEntity.SpecificationBuilder setLetter(String letter) {
            this.letter = letter;
            return this;
        }

        @Override
        public Specification <QuestionTagEntity> build() {
            return (root, query, cb) -> where(fieldStartWith(LIBELLE, letter))
                    .and(fieldEquals(CREATED_BY, principal)
                    ).toPredicate(root, query, cb);
        }

    }

}
