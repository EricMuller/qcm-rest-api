package com.emu.apps.qcm.webmvc.rest.controllers;

import com.emu.apps.shared.security.PrincipalUtils;
import com.emu.apps.qcm.webmvc.rest.UserRestApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Profile("webmvc")
public class UserRestController implements UserRestApi {

    @Value("${spring.profiles.active}")
    private String profiles;

    @Override
    public Map <String, String> user(Principal principal) {
        Map <String, String> map = new LinkedHashMap <>();
        if (Objects.nonNull(principal)) {
            map.put("name", PrincipalUtils.getEmail(principal));
            map.put("profiles", profiles);
        }
        return map;
    }

}
