package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.UUID;

import static java.util.Objects.isNull;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class IdentifiableEntity implements Serializable {

    @Column(name = "UUID", nullable = false, updatable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    @NaturalId
    private UUID uuid; // = UUID.randomUUID();

    @Version
    @Column(name = "VERSION")
    private Long version;

    public IdentifiableEntity() {
    }

    public IdentifiableEntity(UUID uuid) {
        this.uuid = uuid;
    }

    public abstract Long getId();

    public UUID getUuid() {
        return uuid;
    }

    protected void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @PrePersist
    public void prePersist() {
        if (isNull(uuid)) {
            this.uuid = UUID.randomUUID();
        }
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
