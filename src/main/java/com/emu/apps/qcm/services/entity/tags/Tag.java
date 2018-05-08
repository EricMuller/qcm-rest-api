package com.emu.apps.qcm.services.entity.tags;

import com.emu.apps.qcm.services.entity.common.RefEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tag extends RefEntity {

    @Column
    private String libelle;

    @Column
    private boolean publique;

    @OneToMany(mappedBy = "tag")
    private Set<QuestionTag> questionTags = new HashSet<>();

    public Tag() {
    }

    public Tag(String libelle, boolean publique) {
        this.libelle = libelle;
        this.publique = publique;
    }

    public Set<QuestionTag> getQuestionTags() {
        return questionTags;
    }

    public void setQuestionTags(Set<QuestionTag> questionTags) {
        this.questionTags = questionTags;
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


}
