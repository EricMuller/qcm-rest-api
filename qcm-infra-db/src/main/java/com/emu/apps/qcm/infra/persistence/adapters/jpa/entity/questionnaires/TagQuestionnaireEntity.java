package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires;


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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static org.springframework.data.jpa.domain.Specification.where;

@Entity
@BatchSize(size = 20)
@Table(name = "TAG_QUESTIONNAIRE",
        indexes = {@Index(name = "IDX_TAG_QCM_LIBELLE_IDX", columnList = "libelle"),
                @Index(name = "IDX_TAG_QCM_UUID_IDX", columnList = "uuid")}
)
@Getter
@Setter
@NoArgsConstructor
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class TagQuestionnaireEntity extends AuditableEntity <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_qcm_generator")
    @SequenceGenerator(name = "tag_qcm_generator", sequenceName = "tag_qcm_seq", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "LIBELLE")
    private String libelle;

    public TagQuestionnaireEntity(String libelle) {
        this.libelle = libelle;
    }

    public TagQuestionnaireEntity(String libelle, String principal) {
        this.libelle = libelle;
        this.createdBy = principal;
    }

    public static final class SpecificationBuilder extends BaseSpecification <TagQuestionnaireEntity> {

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
        public Specification <TagQuestionnaireEntity> build() {
            return (root, query, cb) -> where(fieldStartWith(LIBELLE, letter))
                    .and(fieldEquals(CREATED_BY, principal)
                    ).toPredicate(root, query, cb);
        }

    }

}
