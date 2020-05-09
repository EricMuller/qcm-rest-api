package com.emu.apps.qcm.webmvc.rest.controllers;

import com.emu.apps.qcm.business.UserDelegate;
import com.emu.apps.qcm.web.dtos.UserDto;
import com.emu.apps.qcm.webmvc.rest.RestMapping;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@Profile("webmvc")
@RequestMapping(RestMapping.USERS)
public class UserRestController {

    private UserDelegate userDelegate;

    public Map <String, String> principal(Principal principal) {
        return userDelegate.principal(principal);
    }

    /**
     * @param principal
     * @return
     */
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDto user(Principal principal) {
        return userDelegate.user(principal);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDto updateUser(@RequestBody UserDto userDto, Principal principal) {
        return userDelegate.updateUser(userDto, principal);
    }

}
