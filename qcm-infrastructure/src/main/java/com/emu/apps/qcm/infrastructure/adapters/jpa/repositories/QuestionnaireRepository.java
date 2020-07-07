package com.emu.apps.qcm.infrastructure.adapters.jpa.repositories;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.infrastructure.adapters.jpa.projections.QuestionnaireProjection;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by eric on 05/06/2017.
 */
@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long>, JpaSpecificationExecutor<Questionnaire> {

    @Query("SELECT q.uuid as uuid, q.version as version,  q.dateCreation as dateCreation, q.dateModification as dateModification, q.title as title, q.category as category ,q.status as status from Questionnaire q  WHERE q.id = :id ")
    QuestionnaireProjection findQuestionnaireById(@Param("id") Long questionnaireId);

    Iterable<QuestionnaireProjection> findByTitleContaining(String title);

    @EntityGraph(value = "Questionnaire.questionnaireTags")
    @NotNull
    Page<Questionnaire> findAll(@NotNull Pageable pageable);

    @Override
    @EntityGraph(value = "Questionnaire.questionnaireTags")
    @NotNull
    Optional<Questionnaire> findById(@NotNull Long id);

    @EntityGraph(value = "Questionnaire.questionnaireTags")
    @NotNull
    Optional<Questionnaire> findByUuid(@NotNull UUID uuid);

    @EntityGraph(value = "Questionnaire.questionnaire")
    Optional<Questionnaire> getById(Long id);

    @Override
    @NotNull
    Page<Questionnaire> findAll(Specification <Questionnaire> specification, @NotNull Pageable pageable);

    void deleteByUuid(UUID uuid);

    @Query("SELECT distinct q.category.libelle FROM Questionnaire  q  WHERE q.published = true")
    Iterable<String> findAllDistinctCategory_LibelleByPublishedTrue();


    Iterable<QuestionnaireProjection> findAllByPublishedTrue();

}
