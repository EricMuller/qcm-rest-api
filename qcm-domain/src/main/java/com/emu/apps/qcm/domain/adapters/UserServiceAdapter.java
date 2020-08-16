package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.api.models.User;
import com.emu.apps.qcm.domain.ports.UserServicePort;
import com.emu.apps.qcm.spi.persistence.UserPersistencePort;
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
    public User user(Principal principal) {
        User userDto;
        if (Objects.nonNull(principal)) {
            String email = PrincipalUtils.getEmail(principal);
            userDto = userPersistencePort.findByEmailContaining(email);
            if (Objects.isNull(userDto)) {
                userDto = new User();
                userDto.setEmail(email);
            }
        } else {
            userDto = new User();
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
    public User updateUser(@RequestBody User userDto, Principal principal) {

        return userPersistencePort.save(userDto);

    }

}
