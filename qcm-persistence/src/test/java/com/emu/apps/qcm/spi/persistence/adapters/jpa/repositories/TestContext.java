package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = SpringBootTestConfig.class)
@ActiveProfiles(value = "test")
public class TestContext {

    @Test
    @Transactional
    public void TestContext()  {


    }

}
