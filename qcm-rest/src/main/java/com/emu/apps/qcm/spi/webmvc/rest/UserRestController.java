package com.emu.apps.qcm.spi.webmvc.rest;

import com.emu.apps.qcm.api.models.User;
import com.emu.apps.qcm.domain.ports.UserServicePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.PUBLIC_API;
import static com.emu.apps.qcm.spi.webmvc.rest.ApiRestMappings.USERS;

@RestController
@Profile("webmvc")
@RequestMapping(PUBLIC_API + USERS)
@Tag(name = "User")
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
    public User user(Principal principal) {
        return userServicePort.user(principal);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User updateUser(@RequestBody User userDto, Principal principal) {
        return userServicePort.updateUser(userDto, principal);
    }

}
