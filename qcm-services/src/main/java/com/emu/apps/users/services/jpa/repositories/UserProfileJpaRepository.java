package com.emu.apps.users.services.jpa.repositories;

import com.emu.apps.users.services.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileJpaRepository extends JpaRepository<UserProfile, Integer> {
    UserProfile findByPrincipalId(String principalId);
    UserProfile findByUsername(String userName);
}
