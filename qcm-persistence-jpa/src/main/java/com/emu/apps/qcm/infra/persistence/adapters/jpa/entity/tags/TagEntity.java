package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.tags;


import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.AuditableEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;

import static org.springframework.data.jpa.domain.Specification.where;

@Entity
@BatchSize(size = 20)
@Table(name = "tag",
        indexes = {@Index(name = "IDX_TAG_LIBELLE_IDX", columnList = "libelle"),
        @Index(name = "IDX_TAG_UUID_IDX", columnList = "uuid")}
)
@Getter
@Setter
@NoArgsConstructor
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class TagEntity extends AuditableEntity <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_generator")
    @SequenceGenerator(name = "tag_generator", sequenceName = "tag_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column
    private String libelle;

    @Column
    private boolean publique;

    public TagEntity(String libelle, boolean publique) {
        this.libelle = libelle;
        this.publique = publique;
    }


    public static final class SpecificationBuilder extends BaseSpecification <TagEntity> {

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
        public Specification <TagEntity> build() {
            return (root, query, cb) -> where(fieldStartWith(LIBELLE, letter))
                    .and(fieldEquals(CREATED_BY, principal)
                    ).toPredicate(root, query, cb);
        }

    }

}