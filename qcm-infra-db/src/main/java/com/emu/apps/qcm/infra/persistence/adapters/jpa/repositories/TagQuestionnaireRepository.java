package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.TagQuestionnaireEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.TagQuestionEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface TagQuestionnaireRepository extends PagingAndSortingRepository<TagQuestionnaireEntity, Long>, JpaSpecificationExecutor<TagQuestionEntity> {

    @Query("SELECT DISTINCT q FROM TagQuestionnaireEntity q WHERE q.libelle  = :libelle and q.createdBy= :principal ")
    TagQuestionnaireEntity findByLibelle(@Param("libelle") String libelle, @Param("principal") String principal);

    Iterable<TagQuestionnaireEntity> findByLibelleContaining(String libelle);

    Optional<TagQuestionnaireEntity> findByUuid(UUID uuid);

    void deleteByCreatedByEquals(String user);


}
