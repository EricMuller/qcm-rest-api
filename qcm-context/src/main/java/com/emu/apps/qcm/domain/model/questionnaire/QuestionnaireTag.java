package com.emu.apps.qcm.domain.model.questionnaire;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireTag {

    private String uuid;

    private String libelle;

    private String createdBy;

    private ZonedDateTime dateCreation;

    private String lastModifiedBy;

    private ZonedDateTime dateModification;
}
