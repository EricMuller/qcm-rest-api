package com.emu.apps.qcm.web.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionTagDto {

    @JsonProperty("deleted")
    private boolean deleted;

    private Long tagId;

    private String libelle;

    private boolean publique;

    private Long questionId;
//
//    @JsonProperty("tag")
//    private TagDto tag;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public boolean isPublique() {
        return publique;
    }

    public void setPublique(boolean publique) {
        this.publique = publique;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
