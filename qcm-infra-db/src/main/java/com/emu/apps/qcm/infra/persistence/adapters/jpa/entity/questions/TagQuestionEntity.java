package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions;


import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.AuditableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import static org.springframework.data.jpa.domain.Specification.where;

@Entity
@BatchSize(size = 20)
@Table(name = "TAG_QUESTION",
        indexes = {@Index(name = "IDX_TAG_Q_LIBELLE_IDX", columnList = "libelle"),
                @Index(name = "IDX_TAG_Q_UUID_IDX", columnList = "uuid")}
)
@Getter
@Setter
@NoArgsConstructor
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class TagQuestionEntity extends AuditableEntity <String> {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LIBELLE")
    private String libelle;

    public TagQuestionEntity(String libelle) {
        this.libelle = libelle;
    }

    public TagQuestionEntity(String libelle, String principal) {
        this.libelle = libelle;
        this.createdBy = principal;
    }

    public static final class SpecificationBuilder extends BaseSpecification <TagQuestionEntity> {

        private String letter;

        private String principal;

        public SpecificationBuilder() {
            //nope
        }

        public SpecificationBuilder setPrincipal(String principal) {
            this.principal = principal;
            return this;
        }

        public SpecificationBuilder setLetter(String letter) {
            this.letter = letter;
            return this;
        }

        @Override
        public Specification <TagQuestionEntity> build() {
            return (root, query, cb) -> where(fieldStartWith(LIBELLE, letter))
                    .and(fieldEquals(CREATED_BY, principal)
                    ).toPredicate(root, query, cb);
        }

    }

}
