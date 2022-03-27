package com.emu.apps.qcm.infra.persistence.adapters.mappers;

import com.emu.apps.qcm.domain.model.base.DomainId;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.UUID;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface UuidMapper {
    Logger logger = LoggerFactory.getLogger(UuidMapper.class);

    default String toString(final UUID uuid) {
        return uuid.toString();
    }

    default UUID toUUID(final String uuid) {
        return Objects.nonNull(uuid) ? UUID.fromString(uuid) : null;
    }

    UUID[] toUUIDs(String[] uuids);

    default UUID getUuid(DomainId domainVersionedId) {

        if (Objects.nonNull(domainVersionedId) && Objects.nonNull(domainVersionedId.getId().toUuid())) {
            try {
                return UUID.fromString(domainVersionedId.getId().toUuid());
            } catch (IllegalArgumentException e) {
                logger.error("{} uuid={}", e.getMessage(), domainVersionedId.getId());
                throw e;
            }
        }
        return null;
    }
}

