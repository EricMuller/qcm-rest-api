package com.emu.apps.users.web.rest.controllers;

import com.emu.apps.users.web.rest.UserRestApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserRestController implements UserRestApi {

    @Value("${spring.profiles.active}")
    private String profiles;

    @Override
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        if (Objects.nonNull(principal)) {
            map.put("name", principal.getName());
            map.put("profiles", profiles);
        }
        return map;
    }

}
