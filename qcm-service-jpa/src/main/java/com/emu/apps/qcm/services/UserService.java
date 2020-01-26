package com.emu.apps.qcm.services;

import com.emu.apps.qcm.services.entity.users.User;

public interface UserService {

    User save(User tag);

    User  findByEmailContaining(String email);
}
