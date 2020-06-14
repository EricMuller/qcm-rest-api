package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.domain.ports.UserServicePort;
import com.emu.apps.qcm.infrastructure.ports.UserPersistencePort;
import com.emu.apps.qcm.domain.dtos.UserDto;
import com.emu.apps.shared.security.PrincipalUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * User Business Delegate
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
public class UserServiceAdapter implements UserServicePort {

    @Value("${spring.profiles.active}")
    private String profiles;

    private final UserPersistencePort userPersistencePort;

    public UserServiceAdapter(UserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public Map <String, String> principal(Principal principal) {
        Map <String, String> map = new LinkedHashMap <>();
        if (Objects.nonNull(principal)) {
            map.put("name", PrincipalUtils.getEmail(principal));
            map.put("profiles", profiles);
        }
        return map;
    }

    /**
     * @param principal principal
     * @return the current user
     */
    @Override
    public UserDto user(Principal principal) {
        UserDto userDto;
        if (Objects.nonNull(principal)) {
            String email = PrincipalUtils.getEmail(principal);
            userDto = userPersistencePort.findByEmailContaining(email);
            if (Objects.isNull(userDto)) {
                userDto = new UserDto();
                userDto.setEmail(email);
            }
        } else {
            userDto = new UserDto();
        }

        return userDto;
    }

    /**
     * Update a user
     *
     * @param userDto   user
     * @param principal principal
     * @return the updated user DTO
     */

    @Override
    public UserDto updateUser(@RequestBody UserDto userDto, Principal principal) {

        return userPersistencePort.save(userDto);

    }

}
