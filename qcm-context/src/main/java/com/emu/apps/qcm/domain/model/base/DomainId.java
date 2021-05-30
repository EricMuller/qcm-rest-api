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
public abstract class DomainId<ID extends DomainObjectId> implements IdentifiableDomainObject <ID> {

    private ID id;

    private Long version;

    private ZonedDateTime dateCreation;

    private ZonedDateTime dateModification;

    protected DomainId(@NonNull ID id) {
        this.id = Objects.requireNonNull(id, "id must not be null");
    }

}
