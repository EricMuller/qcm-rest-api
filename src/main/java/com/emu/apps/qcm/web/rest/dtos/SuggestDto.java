package com.emu.apps.qcm.web.rest.dtos;


import io.swagger.annotations.ApiModel;

@ApiModel(value = "Suggest")
public class SuggestDto {

    private String fieldName;

    private String libelle;

    private Long id;

    public SuggestDto() {
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


}
