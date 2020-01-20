package com.emu.apps.qcm.services.entity.category;

import com.emu.apps.qcm.services.entity.common.RefEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by eric on 05/06/2017.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category extends RefEntity {

    private String libelle;

    public Category(String libelle) {
        this.libelle = libelle;
    }

}
