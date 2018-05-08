package com.emu.apps.qcm.web.rest.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * Created by eric on 05/06/2017.
 */
@ApiModel(value = "Epic")
public class EpicDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("libelle")
    private String libelle;

    public EpicDto() {
            //nope sonar
    }

    public EpicDto(String id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
