package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.UUID;

import static java.util.Objects.isNull;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
public abstract class IdentifiableEntity<T> implements Serializable {

    @Column(name = "UUID", nullable = false, updatable = false)
    @NaturalId
    protected UUID uuid;

    @Version
    @Column(name = "VERSION")
    private Long version;

    protected IdentifiableEntity() {
    }

    protected IdentifiableEntity(UUID uuid) {
        this.uuid = uuid;
    }

    public abstract T getId();

    @PrePersist
    public void prePersist() {
        if (isNull(uuid)) {
            this.uuid = UUID.randomUUID();
        }
    }

}
