package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.TagEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface TagRepository extends PagingAndSortingRepository<TagEntity, Long>, JpaSpecificationExecutor<TagEntity> {

    @Query("SELECT DISTINCT q FROM TagEntity q WHERE q.libelle  = :libelle and q.createdBy= :principal ")
    TagEntity findByLibelle(@Param("libelle") String libelle, @Param("principal") String principal);

    Iterable<TagEntity> findByLibelleContaining(String libelle);

    Optional<TagEntity> findByUuid(UUID uuid);

}
