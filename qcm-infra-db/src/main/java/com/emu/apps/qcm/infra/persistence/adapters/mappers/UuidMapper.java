package com.emu.apps.qcm.infra.persistence.adapters.mappers;

import com.emu.apps.qcm.domain.model.base.DomainId;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;
import java.util.UUID;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UuidMapper {

    default String toString(final UUID uuid) {
        return uuid.toString();
    }

    default UUID toUUID(final String uuid) {
        return Objects.nonNull(uuid) ? UUID.fromString(uuid) : null;
    }

    UUID[] toUUIDs(String[] uuids);

    default  UUID getUuid(DomainId domainVersionedId) {

        if (Objects.nonNull(domainVersionedId) && Objects.nonNull(domainVersionedId.getId().toUuid())) {
            return UUID.fromString (domainVersionedId.getId().toUuid());
        }
        return null;
    }
}

