package com.emu.apps.qcm.domain.dtos;


import com.emu.apps.qcm.domain.dtos.DomainDto;
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
public class TagDto extends DomainDto {

    @JsonProperty("libelle")
    private String libelle;


}
