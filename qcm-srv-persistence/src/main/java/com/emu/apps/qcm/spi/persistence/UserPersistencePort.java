package com.emu.apps.qcm.spi.persistence;

import com.emu.apps.qcm.aggregates.User;

public interface UserPersistencePort {

    User save(User tag);

    User findByEmailEquals(String email);
}
