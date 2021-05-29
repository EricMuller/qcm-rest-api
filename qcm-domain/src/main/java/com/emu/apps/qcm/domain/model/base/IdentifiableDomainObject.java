package com.emu.apps.qcm.domain.model.base;

import java.io.Serializable;

/**
 * Interface for domain objects that can be uniquely identified.
 *
 * @param <ID> the ID type.
 */
public interface IdentifiableDomainObject<ID extends Serializable> extends DomainObject {

    /**
     * Returns the ID of this domain object.
     *
     * @return the ID or {@code null} if an ID has not been assigned yet.
     */
    ID getId();
}
