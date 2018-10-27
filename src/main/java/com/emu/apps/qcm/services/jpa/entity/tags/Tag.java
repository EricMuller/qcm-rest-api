package com.emu.apps.qcm.services.jpa.entity.tags;

import com.emu.apps.qcm.services.jpa.entity.common.AuditableEntity;
import org.hibernate.annotations.BatchSize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@BatchSize(size = 20)
@Table(indexes = {@Index(name = "IDX_TAG_LIBELLE_IDX", columnList = "libelle")})
public class Tag extends AuditableEntity<String> {

    @Column
    private String libelle;

    @Column
    private boolean publique;

    public Tag() {
    }

    public Tag(String libelle, boolean publique) {
        this.libelle = libelle;
        this.publique = publique;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public boolean isPublique() {
        return publique;
    }

    public void setPublique(boolean publique) {
        this.publique = publique;
    }

}
