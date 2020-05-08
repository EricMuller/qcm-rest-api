package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.qcm.services.entity.users.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends PagingAndSortingRepository <User, Long>, JpaSpecificationExecutor <User> {

    User findByEmailContaining(String libelle);

}
