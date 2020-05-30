package com.emu.apps.qcm.infrastructure.ports;


import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.UserProfile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserProfileDOService extends UserDetailsService {

    UserProfile findByPrincipalId(String principalId);

    UserProfile save(UserProfile user);

    @Override
    UserDetails loadUserByUsername(String userName);
}
