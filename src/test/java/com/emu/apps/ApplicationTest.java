package com.emu.apps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@WebAppConfiguration
public class ApplicationTest {

    public static final String USER_TEST = "user";
    public static final String USER_PASSWORD = "password";

    @Test
    public void contextLoads() {
    }

}
