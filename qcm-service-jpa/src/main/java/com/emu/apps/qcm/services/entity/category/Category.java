package com.emu.apps.qcm.services.entity.category;

import com.emu.apps.qcm.services.entity.common.AuditableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by eric on 05/06/2017.
 */

@Entity
@Table(indexes = {@Index(name = "IDX_CTG_CREATE_BY_IDX", columnList = "type,created_by")})
@NoArgsConstructor @Getter @Setter
public class Category extends AuditableEntity <String> {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
    @SequenceGenerator(name = "category_generator", sequenceName = "category_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    protected String libelle;

    @Enumerated(EnumType.STRING)
    private Type type;

    public Category(Type type, String libelle) {
        this.type = type;
        this.libelle = libelle;
    }

}
