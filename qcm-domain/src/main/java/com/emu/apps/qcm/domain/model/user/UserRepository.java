package com.emu.apps.qcm.domain.model.user;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.user.User;

import java.security.Principal;
import java.util.Map;

public interface UserRepository {
    Map <String, String> principal(Principal principal);

    User userByEmail(String email);

    User updateUser(User user, PrincipalId principal);

    User createUser(User user, PrincipalId principal);
}
