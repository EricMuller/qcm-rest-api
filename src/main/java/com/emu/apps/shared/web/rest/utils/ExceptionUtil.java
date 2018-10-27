package com.emu.apps.shared.web.rest.utils;

import com.emu.apps.qcm.services.jpa.entity.common.AuditableEntity;
import com.emu.apps.shared.web.rest.exceptions.ResourceNotFoundException;

import java.util.Objects;

public final class ExceptionUtil {

    private ExceptionUtil() {
        //nope
    }

    public static void assertFound(AuditableEntity entity, String message) {
        if (Objects.isNull(entity)) {
            throw new ResourceNotFoundException(message);
        }
    }
}
