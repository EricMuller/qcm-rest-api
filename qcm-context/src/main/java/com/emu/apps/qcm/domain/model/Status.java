package com.emu.apps.qcm.domain.model;

import java.util.Arrays;

public enum Status {

    DRAFT, TO_BE_VALIDATED, REJECTED, VALIDATED;

    Status() {

    }

    public static Status getByName(String name) {
        return Arrays.stream(values())
                .filter(status -> status.name().equals(name))
                .findFirst()
                .orElse(DRAFT);
    }


}
