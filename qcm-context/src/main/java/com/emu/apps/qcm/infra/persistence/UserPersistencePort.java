package com.emu.apps.qcm.infra.persistence;

import com.emu.apps.qcm.domain.model.user.Account;

public interface UserPersistencePort {

    Account save(Account tag);

    Account findByEmailEquals(String email);

    Account findById(String id) ;
}
