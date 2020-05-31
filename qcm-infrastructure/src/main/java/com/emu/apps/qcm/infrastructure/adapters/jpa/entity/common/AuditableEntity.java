package com.emu.apps.qcm.infrastructure.adapters.jpa.entity.common;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity<U extends Serializable> implements Serializable {

    @Column(name = "created_by")
    @CreatedBy
    protected U createdBy;

    @Column(name = "modified_by")
    @LastModifiedBy
    private U lastModifiedBy;

    @SuppressWarnings("squid:S3437")
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime dateCreation;

    @SuppressWarnings("squid:S3437")
    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime dateModification;

    @Column(unique = true, name = "uuid", nullable = false)
    private UUID uuid = UUID.randomUUID();

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

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
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
