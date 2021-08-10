package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface AccountRepository extends PagingAndSortingRepository <AccountEntity, UUID>, JpaSpecificationExecutor <AccountEntity> {

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Optional<AccountEntity> findByEmailEquals(String libelle);


    Optional<AccountEntity> findByUserNameEquals(String userName);

    void deleteByEmailEquals(String email);

}
