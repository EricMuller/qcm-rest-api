package com.emu.apps.qcm.repositories;

import com.emu.apps.qcm.aggregates.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.Map;

public interface UserRepository {
    Map <String, String> principal(Principal principal);

    User userByEmail(String email);

    User updateUser(@RequestBody User userDto, String principal);

    User createUser(@RequestBody User userDto, String principal);
}
