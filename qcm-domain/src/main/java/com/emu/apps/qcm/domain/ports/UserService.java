package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.web.dtos.UserDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.Map;

public interface UserService {
    Map <String, String> principal(Principal principal);

    UserDto user(Principal principal);

    UserDto updateUser(@RequestBody UserDto userDto, Principal principal);
}
