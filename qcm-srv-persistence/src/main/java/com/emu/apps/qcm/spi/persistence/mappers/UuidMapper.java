package com.emu.apps.qcm.spi.persistence.mappers;

import com.emu.apps.qcm.aggregates.Domain;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;
import java.util.UUID;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UuidMapper {
    default String toString(final UUID persistable) {
        return persistable.toString();
    }

    default UUID toUUID(final String persistable) {
        return Objects.nonNull(persistable) ? UUID.fromString(persistable) : null;
    }

    default  UUID getUuid(Domain entityDto) {

        if (Objects.nonNull(entityDto) && Objects.nonNull(entityDto.getUuid())) {
            return UUID.fromString (entityDto.getUuid());
        }
        return null;
    }

    UUID[] toUUIDs(String[] uuids);


}

