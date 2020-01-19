package com.emu.apps.qcm.webmvc.services;

import com.emu.apps.qcm.H2TestProfileJPAConfig;
import com.emu.apps.qcm.SpringBootTestConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Principal;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootTestConfig.class)
@ActiveProfiles(value = "test")
public class SpringBootTestCase {

    @Autowired
    private  FixtureService fixtureService;

    private Principal principal = () -> H2TestProfileJPAConfig.USER_TEST;

    public Principal getPrincipal() {
        return principal;
    }

    public FixtureService getFixtureService() {
        return fixtureService;
    }
}
