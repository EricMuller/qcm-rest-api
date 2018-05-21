package com.emu.apps.qcm.web.rest.utils;

import java.util.Objects;

public final class DtoUtil {
    private  DtoUtil() {
    }

    public static boolean isUpdate(Long id){
        return Objects.nonNull(id) && Long.valueOf(0).equals(id);
    }
}
