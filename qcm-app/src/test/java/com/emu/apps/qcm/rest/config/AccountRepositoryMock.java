package com.emu.apps.qcm.rest.config;

import com.emu.apps.qcm.domain.model.user.Account;
import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.user.AccountId;
import com.emu.apps.qcm.domain.model.user.AccountRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig.USER_TEST_ID;

@Component
@Primary
public class AccountRepositoryMock implements AccountRepository {

    @Override
    public Map <String, String> principal(Principal principal) {
        Map map = new HashMap();
        map.put(principal, principal);
        return map;
    }

    @Override
    public Account userByEmail(String email) {
        return createUSer();
    }

    @Override
    public Account updateUser(Account account, PrincipalId principal) {
        return createUSer();
    }

    @Override
    public Account createUser(Account account, PrincipalId principal) {
        return createUSer();
    }

    private Account createUSer() {
        Account account = new Account();
        account.setUserName(USER_TEST_ID.toUuid());
        account.setEmail(USER_TEST_ID.toUuid());
        account.setId(new AccountId(USER_TEST_ID.toUuid()));
        return account;
    }

}
