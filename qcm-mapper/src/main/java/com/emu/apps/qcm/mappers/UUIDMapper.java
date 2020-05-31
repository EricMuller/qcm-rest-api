package com.emu.apps.qcm.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;
import java.util.UUID;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UUIDMapper {
    default String toString(final UUID persistable) {
        return persistable.toString();
    }

    default UUID toUUID(final String persistable) {
        return Objects.nonNull(persistable) ? UUID.fromString(persistable) : null;
    }
}

