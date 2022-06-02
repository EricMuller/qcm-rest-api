package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.domain.model.account.AccountId;
import com.emu.apps.qcm.domain.model.account.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

import static com.emu.apps.qcm.rest.config.H2TestProfileJPAConfig.USERNAME_TEST;
import static java.util.Objects.isNull;

@Component
public class DbRepositoryFixture {

    private final AccountRepository accountRepository;

    public DbRepositoryFixture(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public synchronized Account createAccountTest() {

        var account = accountRepository.getAccountByEmail(USERNAME_TEST);

        if (isNull(account)) {
            account = new Account();
            account.setUserName(USERNAME_TEST);
            account.setEmail(USERNAME_TEST);
            account.setId(AccountId.of(UUID.randomUUID()));
            account = accountRepository.createAccount(account);
        }

        return account;
    }
}
