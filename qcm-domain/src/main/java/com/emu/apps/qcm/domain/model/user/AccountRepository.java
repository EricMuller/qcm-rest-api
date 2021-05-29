package com.emu.apps.qcm.domain.model.user;

import com.emu.apps.qcm.domain.model.base.PrincipalId;

import java.security.Principal;
import java.util.Map;

public interface AccountRepository {
    Map <String, String> principal(Principal principal);

    Account userByEmail(String email);

    Account updateUser(Account account, PrincipalId principal);

    Account createUser(Account account, PrincipalId principal);
}
