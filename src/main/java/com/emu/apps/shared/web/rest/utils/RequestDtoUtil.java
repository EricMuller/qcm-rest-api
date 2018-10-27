package com.emu.apps.shared.web.rest.utils;

import java.util.Objects;

public final class RequestDtoUtil {
    private RequestDtoUtil() {
    }

    public static boolean isUpdate(Long id){
        return Objects.nonNull(id) && Long.valueOf(0).equals(id);
    }
}
