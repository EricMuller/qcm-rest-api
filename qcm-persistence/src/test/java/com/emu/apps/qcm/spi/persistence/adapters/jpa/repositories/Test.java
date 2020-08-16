package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootTestConfig;
import com.emu.apps.qcm.spi.persistence.exceptions.FunctionnalException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = SpringBootTestConfig.class)
@ActiveProfiles(value = "test")
public class Test {

    private static final String USER_TEST = SpringBootTestConfig.USER_TEST;

    @org.junit.jupiter.api.Test
    @Transactional
    public void findOrCreateByLibelle() throws FunctionnalException {



    }

}
