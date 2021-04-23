package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.model.user.User;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.user.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig.USER_TEST;

@Component
@Primary
public class UserRepositoryMock implements UserRepository {

    @Override
    public Map <String, String> principal(Principal principal) {
        Map map = new HashMap();
        map.put(principal, principal);
        return map;
    }

    @Override
    public User userByEmail(String email) {
        return createUSer();
    }

    @Override
    public User updateUser(User user, PrincipalId principal) {
        return createUSer();
    }

    @Override
    public User createUser(User user, PrincipalId principal) {
        return createUSer();
    }

    private User createUSer() {
        User user = new User();
        user.setUserName(USER_TEST.toUUID());
        user.setEmail(USER_TEST.toUUID());
        user.setUuid(USER_TEST.toUUID());
        return user;
    }

}
