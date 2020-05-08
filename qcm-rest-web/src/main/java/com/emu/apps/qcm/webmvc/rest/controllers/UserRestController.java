package com.emu.apps.qcm.webmvc.rest.controllers;

import com.emu.apps.qcm.web.dtos.UserDto;
import com.emu.apps.qcm.business.UserDelegate;
import com.emu.apps.qcm.webmvc.rest.UserRestApi;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@Profile("webmvc")
public class UserRestController implements UserRestApi {

    private UserDelegate userDelegate;


    public Map <String, String> principal(Principal principal) {

        return userDelegate.principal(principal);

    }

    /**
     * @param principal
     * @return
     */
    public UserDto user(Principal principal) {

        return userDelegate.user(principal);


    }

    public UserDto updateUser(@RequestBody UserDto userDto, Principal principal) {

        return userDelegate.updateUser(userDto, principal);

    }

}
