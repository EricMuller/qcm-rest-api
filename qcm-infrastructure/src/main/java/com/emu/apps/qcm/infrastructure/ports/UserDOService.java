package com.emu.apps.qcm.infrastructure.ports;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.users.User;

public interface UserDOService {

    User save(User tag);

    User  findByEmailContaining(String email);
}
