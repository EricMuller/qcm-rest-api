package com.emu.apps.qcm.domain.models.tag;


import com.emu.apps.qcm.domain.models.base.DomainId;
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
public class Tag extends DomainId {

    @JsonProperty("libelle")
    private String libelle;


}
