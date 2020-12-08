package com.emu.apps.qcm.infra.persistence;

import com.emu.apps.qcm.domain.models.User;

public interface UserPersistencePort {

    User save(User tag);

    User findByEmailEquals(String email);
}
