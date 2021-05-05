package com.emu.apps.qcm.rest.controllers.resources;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * Created by eric on 05/06/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagResources {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("version")
    private Long version;

    @JsonProperty("dateCreation")
    private ZonedDateTime dateCreation;

    @JsonProperty("dateModification")
    private ZonedDateTime dateModification;

    @JsonProperty("libelle")
    private String libelle;


}
