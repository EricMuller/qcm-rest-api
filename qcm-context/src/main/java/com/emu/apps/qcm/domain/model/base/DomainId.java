package com.emu.apps.qcm.domain.model.base;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public abstract class DomainId<T extends DomainObjectId> implements IdentifiableDomainObject <T> {

    private T id;

    private Long version;

    private String createdBy;

    private ZonedDateTime dateCreation;

    private String lastModifiedBy;

    private ZonedDateTime dateModification;

    protected DomainId(@NonNull T id) {
        this.id = Objects.requireNonNull(id, "id must not be null");
    }

}
