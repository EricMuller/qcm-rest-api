package com.emu.apps.qcm.web.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public abstract class EntityDto {

    @ApiModelProperty(notes = "The database generated product ID")
    @JsonProperty("id")
    private Long id;

    @JsonProperty("uuid")
    private  String uuid;

    @ApiModelProperty(notes = "The database generated version record")
    @JsonProperty("version")
    private Long version;

    @ApiModelProperty(notes = "The  Creation Date record")
    @JsonProperty("date_creation")
    private Date dateCreation;

    @ApiModelProperty(notes = "The last Modification Date record")
    @JsonProperty("date_modification")
    private Date dateModification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
