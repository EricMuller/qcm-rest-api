package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.common.AuditableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@BatchSize(size = 20)
@Table(name = "tag",
        indexes = {@Index(name = "IDX_TAG_LIBELLE_IDX", columnList = "libelle")})
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

}
