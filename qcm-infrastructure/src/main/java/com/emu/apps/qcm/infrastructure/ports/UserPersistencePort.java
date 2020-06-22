package com.emu.apps.qcm.infrastructure.ports;

import com.emu.apps.qcm.models.UserDto;

public interface UserPersistencePort {

    UserDto save(UserDto tag);

    UserDto  findByEmailContaining(String email);
}
