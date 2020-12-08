package com.emu.apps.qcm.domain.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by eric on 05/06/2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends Domain {

    @JsonProperty("libelle")
    private String libelle;

    @JsonProperty("type")
    private String type;

    @JsonProperty("userId")
    private String userId;

}
