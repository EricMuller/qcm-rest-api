//package com.emu.apps.qcm.services.entity;
//
//
//import com.emu.apps.qcm.services.entity.reference.CriteriaGroup;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//
///**
// * Created by eric on 05/06/2017.
// */
//
//@Entity
//public class Criteria extends BasicEntity {
//
//
//
//    @Enumerated(EnumType.STRING)
//    private CriteriaGroup group;
//
//    @Column(nullable = false, length = 30)
//    private String fieldName;
//
//    @Column(nullable = false, length = 50)
//    private String libelle;
//
//    public Criteria() {
//    }
//
//    public CriteriaGroup getGroup() {
//        return group;
//    }
//
//    public void setGroup(CriteriaGroup group) {
//        this.group = group;
//    }
//
//    public String getLibelle() {
//        return libelle;
//    }
//
//    public void setLibelle(String libelle) {
//        this.libelle = libelle;
//    }
//
//    public String getFieldName() {
//        return fieldName;
//    }
//
//    public void setFieldName(String fieldName) {
//        this.fieldName = fieldName;
//    }
//}
