package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.Tag;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {

    @Query("SELECT DISTINCT q FROM Tag q WHERE q.libelle  = :libelle and q.createdBy= :principal ")
    Tag findByLibelle(@Param("libelle") String libelle, @Param("principal") String principal);

    Iterable<Tag> findByLibelleContaining(String libelle);

    Optional<Tag> findByUuid(UUID uuid);

}
