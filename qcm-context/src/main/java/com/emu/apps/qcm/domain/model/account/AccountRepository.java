package com.emu.apps.qcm.domain.model.account;

import java.security.Principal;
import java.util.Map;

public interface AccountRepository {
    Map <String, String> principal(Principal principal);

    Account getAccountByEmail(String email);

    Account updateAccount(Account account);

    Account createAccount(Account account);

    Account getAccountById(String id) ;
}
