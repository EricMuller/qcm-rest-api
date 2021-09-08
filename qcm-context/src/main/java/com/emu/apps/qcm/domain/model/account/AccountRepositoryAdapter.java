package com.emu.apps.qcm.domain.model.account;

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
class AccountRepositoryAdapter implements AccountRepository {

    @Value("${spring.profiles.active}")
    private String profiles;

    private final UserPersistencePort userPersistencePort;

    public AccountRepositoryAdapter(UserPersistencePort userPersistencePort) {
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
    public Account getAccountByEmail(String email) {
        return userPersistencePort.findByEmailEquals(email);
    }

    @Override
    public Account userById(String id) {
        return userPersistencePort.findById(id);
    }

    /**
     * Update a user
     *
     * @param account      user
     * @param principal principal
     * @return the updated user DTO
     */

    @Override
    public Account updateAccount(@RequestBody Account account) {

        return userPersistencePort.update(account);

    }

    @Override
    public Account createAccount(@RequestBody Account account) {


        return userPersistencePort.save(account);

    }

}
