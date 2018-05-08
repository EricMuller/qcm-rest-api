package com.emu.apps.qcm.services.impl.jpa;


import com.emu.apps.qcm.services.entity.UserProfile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserProfileServiceImpl extends UserDetailsService {

    UserProfile findByPrincipalId(String principalId);

    UserProfile save(UserProfile user);

    @Override
    UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;
}
