package com.emu.apps.qcm.web.rest.exceptions;

import com.emu.apps.qcm.services.entity.common.BasicEntity;

import java.util.Objects;

public final class ExceptionUtil {

    private ExceptionUtil() {
        //nope
    }

    public static void assertFound(BasicEntity entity, String message) {
        if (Objects.isNull(entity)) {
            throw new ResourceNotFoundException(message);
        }
    }
}
