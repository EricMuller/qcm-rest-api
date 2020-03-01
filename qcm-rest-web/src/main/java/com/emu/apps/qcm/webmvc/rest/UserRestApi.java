package com.emu.apps.qcm.webmvc.rest;

import com.emu.apps.qcm.web.dtos.UserDto;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;


@RequestMapping(UserApi.API_V1 + "/users")
public interface UserRestApi {

//    Map <String, String> principal(Principal principal);

    @GetMapping(value = "/me",produces = "application/json")
    @ResponseBody
    UserDto user(Principal principal);

    @PostMapping(produces = "application/json")
    @ResponseBody
    UserDto updateUser(@RequestBody UserDto userDto, Principal principal);


}
