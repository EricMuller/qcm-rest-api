package com.emu.apps.qcm.domain.entity.tags;

import com.emu.apps.qcm.domain.entity.common.AuditableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Entity
@BatchSize(size = 20)
@Table(indexes = {@Index(name = "IDX_TAG_LIBELLE_IDX", columnList = "libelle")})
@Getter
@Setter
@NoArgsConstructor
public class Tag extends AuditableEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_generator")
    @SequenceGenerator(name="tag_generator", sequenceName = "tag_seq", allocationSize=50)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column
    private String libelle;

    @Column
    private boolean publique;

    public Tag(String libelle, boolean publique) {
        this.libelle = libelle;
        this.publique = publique;
    }

}
