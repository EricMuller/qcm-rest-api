package com.emu.apps.qcm.domain.model.base;

import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;


/**
 * Base class for value objects that are used as identifiers for {@link IdentifiableDomainObject}s. These are
 * essentially UUID-wrappers.
 */
@NoArgsConstructor
public abstract class DomainObjectId implements ValueObject {

    private String uuid;

    protected DomainObjectId(String uuid) {
        this.uuid = Objects.requireNonNull(uuid, "uuid must not be null");
    }

    /**
     * Creates a new, random instance of the given {@code idClass}.
     */
    public static <T extends DomainObjectId> T randomId(Class <T> idClass) {
        Objects.requireNonNull(idClass, "idClass must not be null");
        try {
            return idClass.getConstructor(String.class).newInstance(UUID.randomUUID().toString());
        } catch (Exception ex) {
            throw new RuntimeException("Could not create new instance of " + idClass, ex);
        }
    }

    /**
     * Returns the ID as a UUID string.
     */
//    @JsonValue
    public String toUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainObjectId that = (DomainObjectId) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), uuid);
    }


}
