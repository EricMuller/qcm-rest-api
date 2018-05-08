package com.emu.apps.qcm.services.impl.jpa;

import com.emu.apps.qcm.services.UserProfileService;
import com.emu.apps.qcm.services.entity.UserProfile;
import com.emu.apps.qcm.services.repositories.UserProfileJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

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
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userProfileRepository.findByUsername(userName);
    }
}
