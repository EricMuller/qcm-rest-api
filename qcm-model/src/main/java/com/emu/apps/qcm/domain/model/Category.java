package com.emu.apps.qcm.domain.model;


import com.emu.apps.qcm.domain.model.base.DomainId;
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
public class Category extends DomainId {

    @JsonProperty("libelle")
    private String libelle;

    @JsonProperty("type")
    private String type;

    @JsonProperty("userId")
    private String userId;

}
