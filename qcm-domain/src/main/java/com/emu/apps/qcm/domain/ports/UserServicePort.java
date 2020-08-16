package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.api.models.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.Map;

public interface UserServicePort {
    Map <String, String> principal(Principal principal);

    User user(Principal principal);

    User updateUser(@RequestBody User userDto, Principal principal);
}
