package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.common;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity<U extends Serializable> implements Serializable {

    @Column(unique = true, name = "uuid", nullable = false, updatable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid = UUID.randomUUID();

    @Column(name = "created_by", updatable = false)
    @CreatedBy
    protected U createdBy;

    @Column(name = "modified_by")
    @LastModifiedBy
    private U lastModifiedBy;

    @SuppressWarnings("squid:S3437")
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private ZonedDateTime dateCreation;

    @SuppressWarnings("squid:S3437")
    @Column(name = "modified_date")
    @LastModifiedDate
    private ZonedDateTime dateModification;

    @Version
    private Long version;

    public abstract Long getId();

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @PrePersist
    public void prePersist() {
        this.setUuid(UUID.randomUUID());
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ZonedDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(ZonedDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
