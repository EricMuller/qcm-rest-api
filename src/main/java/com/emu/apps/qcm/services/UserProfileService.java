package com.emu.apps.qcm.services;


import com.emu.apps.qcm.services.jpa.entity.UserProfile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserProfileService extends UserDetailsService {

    UserProfile findByPrincipalId(String principalId);

    UserProfile save(UserProfile user);

    @Override
    UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;
}
