package com.emu.apps.users.web.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Map;

@CrossOrigin
@RequestMapping(UserApi.API_V1 +"/users/me")
public interface UserRestApi {

    @GetMapping(produces = "application/json")
    Map <String, String> user(Principal principal);
}
