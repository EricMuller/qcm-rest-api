package com.emu.apps.qcm.web.rest.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * Created by eric on 05/06/2017.
 */
@ApiModel(value = "Tag")
public class TagDto extends EntityDto {

    @JsonProperty("libelle")
    private String libelle;

    public TagDto() {
        //nope sonar
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
