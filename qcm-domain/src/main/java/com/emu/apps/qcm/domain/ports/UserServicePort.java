package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.api.models.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.Map;

public interface UserServicePort {
    Map <String, String> principal(Principal principal);

    User userByEmail(String email);

    User updateUser(@RequestBody User userDto, String principal);

    User createUser(@RequestBody User userDto, String principal);
}
