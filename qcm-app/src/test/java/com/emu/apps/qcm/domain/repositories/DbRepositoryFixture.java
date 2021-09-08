package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.domain.model.account.AccountId;
import com.emu.apps.qcm.domain.model.account.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

import static com.emu.apps.qcm.rest.config.H2TestProfileJPAConfig.USERNAME_TEST;

@Component
public class DbRepositoryFixture {

    private final AccountRepository accountRepository;

    public DbRepositoryFixture(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public synchronized Account createAccountTest() {

        Account account = accountRepository.getAccountByEmail(USERNAME_TEST);

        if (Objects.isNull(account)) {
            account = new Account();
            account.setUserName(USERNAME_TEST);
            account.setEmail(USERNAME_TEST);
            account.setId(AccountId.of(UUID.randomUUID()));
            account = accountRepository.createAccount(account);

        }

        return account;
    }
}
