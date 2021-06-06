package com.emu.apps.qcm.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void getByName() {

        Assertions.assertEquals(Status.DRAFT,Status.getByName("test"));
    }
}
