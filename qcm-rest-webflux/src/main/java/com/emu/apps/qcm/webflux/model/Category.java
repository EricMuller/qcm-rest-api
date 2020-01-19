package com.emu.apps.qcm.webflux.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Created by eric on 05/06/2017.
 */

@Getter
@Setter
@NoArgsConstructor
@Table
public class Category {

    @Id
    private Long id;

    private String libelle;

    public Category(String libelle) {
        this.libelle = libelle;
    }

}
