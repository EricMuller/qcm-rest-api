package com.emu.apps.qcm.webmvc.rest.controllers;

import com.emu.apps.qcm.services.UserService;
import com.emu.apps.qcm.services.entity.users.User;
import com.emu.apps.qcm.web.dtos.UserDto;
import com.emu.apps.qcm.web.mappers.UserMapper;
import com.emu.apps.qcm.webmvc.rest.UserRestApi;
import com.emu.apps.shared.security.PrincipalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Profile("webmvc")
public class UserRestController implements UserRestApi {

    @Value("${spring.profiles.active}")
    private String profiles;

    private UserService userService;

    private UserMapper userMapper;

    @Autowired
    public UserRestController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    public Map <String, String> principal(Principal principal) {
        Map <String, String> map = new LinkedHashMap <>();
        if (Objects.nonNull(principal)) {
            map.put("name", PrincipalUtils.getEmail(principal));
            map.put("profiles", profiles);
        }
        return map;
    }

    /**
     * @param principal
     * @return
     */
    public UserDto user(Principal principal) {
        UserDto userDto;
        if (Objects.nonNull(principal)) {
            String email = PrincipalUtils.getEmail(principal);
            User user = userService.findByEmailContaining(email);
            if (Objects.isNull(user)) {
                userDto = new UserDto();
                userDto.setEmail(email);
            } else {
                userDto = userMapper.modelToDto(user);
            }
        } else {
            userDto = new UserDto();
        }

        return userDto;

    }

    public UserDto updateUser(@RequestBody UserDto userDto, Principal principal) {
        var user = userService.findByEmailContaining(userDto.getEmail());
        if (Objects.isNull(user)) {
            user = new User();
        }
        userMapper.dtoToModel(user, userDto);
        return userMapper.modelToDto(userService.save(user));

    }

}
