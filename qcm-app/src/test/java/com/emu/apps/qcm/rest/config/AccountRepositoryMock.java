package com.emu.apps.qcm.rest.config;

import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.domain.model.account.AccountId;
import com.emu.apps.qcm.domain.model.account.AccountRepository;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig.USER_TEST_ID;

//@Component
//@Primary
public class AccountRepositoryMock implements AccountRepository {

    @Override
    public Map <String, String> principal(Principal principal) {
        Map map = new HashMap();
        map.put(principal, principal);
        return map;
    }

    @Override
    public Account getAccountByEmail(String email) {
        return createUSer();
    }

    @Override
    public Account updateAccount(Account account) {
        return createUSer();
    }

    @Override
    public Account createAccount(Account account) {
        return createUSer();
    }

    private Account createUSer() {
        Account account = new Account();
        account.setUserName(USER_TEST_ID.toUuid());
        account.setEmail(USER_TEST_ID.toUuid());
        account.setId(AccountId.of(USER_TEST_ID.toUuid()));
        return account;
    }

    @Override
    public Account userById(String id) {
         return createUSer();
    }
}
