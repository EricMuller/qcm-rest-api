package com.emu.apps.qcm.webmvc.services.jpa.entity.common;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class RefEntity implements ID {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @ApiModelProperty(notes = "The database generated Question ID")
    private Long id;

    @Column(unique = true, name = "uuid", nullable = false)
    protected String uuid = UUID.randomUUID().toString().toUpperCase();

    @Column(name = "CREATED_DATE")
    private Date dateCreation;

    @Column(name = "UPDATED_DATE")
    private Date dateModification;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @PrePersist
    public void prePersist() {
        this.setUuid(UUID.randomUUID().toString());
        if(Objects.isNull(getDateCreation())){
            this.setDateCreation(new Date());
        }
        else{
            this.setDateModification(new Date());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }
}
