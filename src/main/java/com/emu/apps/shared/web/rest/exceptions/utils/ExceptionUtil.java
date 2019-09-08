package com.emu.apps.shared.web.rest.exceptions.utils;

import com.emu.apps.qcm.services.jpa.entity.common.AuditableEntity;
import com.emu.apps.shared.web.rest.exceptions.ResourceNotFoundException;

import java.util.Objects;
import java.util.Optional;

public final class ExceptionUtil {

    private ExceptionUtil() {
        //nope
    }

    public static <T extends AuditableEntity> void assertIsPresent(T  entity, String message) {
        if (Objects.isNull(entity)) {
            throw new ResourceNotFoundException(message);
        }
    }

    public static void assertIsPresent(Optional<? extends AuditableEntity> entity, String message) {
        if (!entity.isPresent()) {
            throw new ResourceNotFoundException(message);
        }
    }
}
