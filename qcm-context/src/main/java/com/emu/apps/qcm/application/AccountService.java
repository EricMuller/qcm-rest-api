package com.emu.apps.qcm.application;

import com.emu.apps.qcm.domain.model.user.Account;
import com.emu.apps.qcm.domain.model.user.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account userByEmail(String email) {
        return accountRepository.userByEmail(email);
    }


}
