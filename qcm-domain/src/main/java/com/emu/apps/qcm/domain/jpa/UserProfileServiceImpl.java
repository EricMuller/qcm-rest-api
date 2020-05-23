package com.emu.apps.qcm.domain.jpa;

import com.emu.apps.qcm.domain.UserProfileDOService;
import com.emu.apps.qcm.domain.entity.UserProfile;
import com.emu.apps.qcm.domain.jpa.repositories.UserProfileJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileDOService {

    @Autowired
    private UserProfileJpaRepository userProfileRepository;

    @Override
    public UserProfile findByPrincipalId(String principalId){
        return userProfileRepository.findByPrincipalId(principalId);
    }

    @Override
    public UserProfile save(UserProfile user){

        return userProfileRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        return userProfileRepository.findByUsername(userName);
    }
}
