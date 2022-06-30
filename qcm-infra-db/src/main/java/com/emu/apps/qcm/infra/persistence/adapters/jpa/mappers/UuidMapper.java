package com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers;

import com.emu.apps.qcm.domain.model.base.DomainId;
import com.emu.apps.qcm.domain.model.base.DomainObjectId;
import com.emu.apps.shared.exceptions.TechnicalException;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static java.util.Objects.nonNull;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface UuidMapper {
    Logger logger = LoggerFactory.getLogger(UuidMapper.class);

    default String toString(final UUID uuid) {
        return uuid.toString();
    }

    default UUID toUUID(final String uuid) {
        return nonNull(uuid) ? UUID.fromString(uuid) : null;
    }

    UUID[] toUUIDs(String[] uuids);

    default UUID getUuid(DomainId <? extends DomainObjectId> domainVersionedId) {

        if (nonNull(domainVersionedId) && nonNull(domainVersionedId.getId().toUuid())) {
            try {
                return UUID.fromString(domainVersionedId.getId().toUuid());
            } catch (IllegalArgumentException e) {
                throw new TechnicalException(String.format("%s uuid=%s", e.getMessage(), domainVersionedId.getId()), e);
            }
        }
        return null;
    }
}

