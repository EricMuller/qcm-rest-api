package com.emu.apps.qcm.domain.jpa.repositories;

import com.emu.apps.qcm.domain.entity.users.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends PagingAndSortingRepository <User, Long>, JpaSpecificationExecutor <User> {

    User findByEmailContaining(String libelle);

}
