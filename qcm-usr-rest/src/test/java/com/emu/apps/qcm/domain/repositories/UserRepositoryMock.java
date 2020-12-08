package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.models.User;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class UserRepositoryMock implements UserRepository {

    @Override
    public Map <String, String> principal(Principal principal) {
        Map map= new HashMap();
        map.put(principal,principal);
        return map;
    }

    @Override
    public User userByEmail(String email) {

        return createUSer();
    }

    @Override
    public User updateUser(User userDto, String principal) {
        return createUSer();

    }

    @Override
    public User createUser(User userDto, String principal) {
        return createUSer();
    }

    private User createUSer() {
        User user = new User();
        user.setUserName(SpringBootJpaTestConfig.USER_TEST);
        user.setEmail(SpringBootJpaTestConfig.USER_TEST);
        user.setUuid(SpringBootJpaTestConfig.USER_TEST);
        return user;
    }
}
