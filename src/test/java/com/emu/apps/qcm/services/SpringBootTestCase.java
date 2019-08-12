package com.emu.apps.qcm.services;

import com.emu.apps.H2TestProfileJPAConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;

@RunWith(SpringRunner.class)
@SpringBootTest
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
