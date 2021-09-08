package com.emu.apps.qcm.application;

import com.emu.apps.qcm.domain.model.account.Account;
import com.emu.apps.qcm.domain.model.account.AccountId;
import com.emu.apps.qcm.domain.model.account.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.getAccountByEmail(email);
    }

    public Account createNewAccount(String email, String userName, AccountId accountId) {
        Account account = new Account();
        account.setEmail(email);
        account.setUserName(userName);
        account.setId(accountId);
        return accountRepository.createAccount(account);
    }

    public Account userById(String id) {
        return accountRepository.getAccountByEmail(id);
    }


}
