package com.emu.apps.users.services;


import com.emu.apps.users.services.entity.UserProfile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserProfileService extends UserDetailsService {

    UserProfile findByPrincipalId(String principalId);

    UserProfile save(UserProfile user);

    @Override
    UserDetails loadUserByUsername(String userName);
}
