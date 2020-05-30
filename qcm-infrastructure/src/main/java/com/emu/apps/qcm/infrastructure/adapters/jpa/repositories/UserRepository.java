package com.emu.apps.qcm.infrastructure.adapters.jpa.repositories;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.users.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends PagingAndSortingRepository <User, Long>, JpaSpecificationExecutor <User> {

    User findByEmailContaining(String libelle);

}
