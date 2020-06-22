package com.emu.apps.qcm.webmvc.rest;

import com.emu.apps.qcm.models.UserDto;
import com.emu.apps.qcm.domain.ports.UserServicePort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.webmvc.rest.ApiRestMappings.USERS;

@RestController
@Profile("webmvc")
@RequestMapping(PUBLIC_API + USERS)
public class UserRestController {

    private final UserServicePort userServicePort;

    public UserRestController(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    public Map <String, String> principal(Principal principal) {
        return userServicePort.principal(principal);
    }

    /**
     * @param principal
     * @return the current UserDto
     */
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDto user(Principal principal) {
        return userServicePort.user(principal);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDto updateUser(@RequestBody UserDto userDto, Principal principal) {
        return userServicePort.updateUser(userDto, principal);
    }

}
