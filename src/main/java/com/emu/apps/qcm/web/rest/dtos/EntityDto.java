package com.emu.apps.qcm.web.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(notes = "The database generated product ID")
    @JsonProperty("id")
    protected Long id;

    @JsonProperty("uuid")
    private  String uuid;

    @ApiModelProperty(notes = "The database generated version record")
    @JsonProperty("version")
    private Long version;

    @ApiModelProperty(notes = "The  Creation Date record")
    @JsonProperty("dateCreation")
    private Date dateCreation;

    @ApiModelProperty(notes = "The last Modification Date record")
    @JsonProperty("dateModification")
    private Date dateModification;


}
