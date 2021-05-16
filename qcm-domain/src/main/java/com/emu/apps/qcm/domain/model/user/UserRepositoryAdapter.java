package com.emu.apps.qcm.domain.model.user;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.infra.persistence.UserPersistencePort;
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
class UserRepositoryAdapter implements UserRepository {

    @Value("${spring.profiles.active}")
    private String profiles;

    private final UserPersistencePort userPersistencePort;

    public UserRepositoryAdapter(UserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public Map <String, String> principal(Principal principal) {
        Map <String, String> map = new LinkedHashMap <>();
        if (Objects.nonNull(principal)) {
            map.put("name", PrincipalUtils.getEmailOrName(principal));
            map.put("profiles", profiles);
        }
        return map;
    }

    /**
     * @param principal principal
     * @return the current user
     */
    @Override
    public User userByEmail(String email) {
        return userPersistencePort.findByEmailEquals(email);

    }

    /**
     * Update a user
     *
     * @param user      user
     * @param principal principal
     * @return the updated user DTO
     */

    @Override
    public User updateUser(@RequestBody User user, PrincipalId principal) {

        return userPersistencePort.save(user);

    }

    @Override
    public User createUser(@RequestBody User user, PrincipalId principal) {

        user.setEmail(principal.toUUID());
        return userPersistencePort.save(user);

    }

}
