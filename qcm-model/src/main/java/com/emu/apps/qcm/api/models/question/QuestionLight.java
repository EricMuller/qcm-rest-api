package com.emu.apps.qcm.api.models.question;


import com.emu.apps.qcm.api.models.Domain;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by eric on 05/06/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class QuestionLight extends Domain {

    @JsonProperty("question")
    private String libelle;

    private String type;

    private String status;
}
