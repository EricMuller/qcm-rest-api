package com.emu.apps.qcm.services;

import com.emu.apps.H2TestProfileJPAConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.security.Principal;

@SpringBootTest
@ActiveProfiles(value = "test")
public class FixtureTest {

    @Autowired
    protected FixtureService fixtureService;

    private Principal principal = () -> H2TestProfileJPAConfig.USER_TEST;

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }


}
