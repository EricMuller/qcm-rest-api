package com.emu.apps.qcm.infra.persistence;

import com.emu.apps.qcm.domain.model.account.Account;

public interface UserPersistencePort {

    Account save(Account account);

    Account update(Account account);

    Account findByEmailEquals(String email);

    Account findById(String id) ;
}
