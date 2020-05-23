package com.emu.apps.qcm.domain;

import com.emu.apps.qcm.domain.entity.users.User;

public interface UserDOService {

    User save(User tag);

    User  findByEmailContaining(String email);
}
