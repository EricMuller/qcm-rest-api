package com.emu.apps.qcm.webmvc.rest;

import com.emu.apps.qcm.domain.adapters.UserServiceAdapter;
import com.emu.apps.qcm.domain.ports.UserService;
import com.emu.apps.qcm.web.dtos.UserDto;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@Profile("webmvc")
@RequestMapping(RestMappings.USERS)
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    public Map <String, String> principal(Principal principal) {
        return userService.principal(principal);
    }

    /**
     *
     * @param principal
     * @return  the current UserDto
     */
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDto user(Principal principal) {
        return userService.user(principal);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDto updateUser(@RequestBody UserDto userDto, Principal principal) {
        return userService.updateUser(userDto, principal);
    }

}
