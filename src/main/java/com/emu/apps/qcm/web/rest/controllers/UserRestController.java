package com.emu.apps.qcm.web.rest.controllers;

import com.emu.apps.qcm.web.rest.UserRestApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserRestController implements UserRestApi {

    @Value("{spring.profiles.active}")
    private String profile;

    @Override
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        if (Objects.nonNull(principal)) {
            map.put("name", principal.getName());
            map.put("profile", profile);
        }
        return map;
    }

}
