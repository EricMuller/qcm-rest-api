package com.emu.apps.qcm.services.entity.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by eric on 05/06/2017.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue(value="QUESTIONNAIRE")
public class QuestionnaireCategory extends Category {

    public QuestionnaireCategory(String libelle) {
        this.libelle = libelle;
    }
}
