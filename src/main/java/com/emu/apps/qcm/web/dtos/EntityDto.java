package com.emu.apps.qcm.web.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class EntityDto {

    @JsonProperty("id")
    protected Long id;

    @JsonProperty("uuid")
    private  String uuid;

    @JsonProperty("version")
    private Long version;

    @JsonProperty("dateCreation")
    private Date dateCreation;

    @JsonProperty("dateModification")
    private Date dateModification;

}
