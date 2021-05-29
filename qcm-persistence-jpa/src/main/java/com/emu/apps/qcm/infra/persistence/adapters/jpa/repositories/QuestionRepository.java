package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository <QuestionEntity, Long>, JpaSpecificationExecutor <QuestionEntity> {

    @Query(value = " SELECT distinct q  from  QuestionEntity  q left join fetch q.tags qt   join fetch qt.tag t",
            countQuery = "SELECT COUNT(distinct q) FROM QuestionEntity q left JOIN  q.tags qt  join  qt.tag t")
    Page <QuestionEntity> findAllQuestionsTags(Pageable pageable);

    @Query("SELECT q from QuestionEntity q left join fetch q.tags qt join fetch qt.tag WHERE q.id = :id ")
    QuestionEntity findByIdAndFetchTags(@Param("id") Long id);

    @Query("SELECT q from QuestionEntity q " +
            "left join fetch q.tags qt " +
            "join fetch qt.tag " +
            "left join fetch q.responses  r " +
            "WHERE q.id = :id ")
    QuestionEntity findByIdAndFetchTagsAndResponses(@Param("id") Long id);


    Optional <QuestionEntity> findByUuid(@NotNull UUID uuid);


    void deleteByUuid(UUID uuid);



    @Override
    @NotNull
    Page <QuestionEntity> findAll(Specification <QuestionEntity> specification, @NotNull Pageable pageable);

    /**
     * @deprecated
     * @param specification
     * @return List of QuestionEntity
     */
    @Query(value = " SELECT distinct q  from  QuestionEntity  q left join fetch q.tags qt   join fetch qt.tag t",
            countQuery = "SELECT COUNT(distinct q) FROM QuestionEntity q left JOIN  q.tags qt  join  qt.tag t")
    @Deprecated(forRemoval = true)
    Iterable <QuestionEntity> findAllQuestionWithTags(Specification <QuestionEntity> specification);


    @Query(value = " SELECT distinct ( q.status ) from  QuestionEntity  q ",
            countQuery = "SELECT COUNT(distinct q.status ) from  QuestionEntity  q  ")
    Page <String> findAllStatusByCreatedBy( String principal,Pageable pageable);

    void deleteByCreatedByEquals(String user);

}
