package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.jpa.projections.QuestionnaireProjection;
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

/**
 * Created by eric on 05/06/2017.
 */
@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long>, JpaSpecificationExecutor<Questionnaire> {

    @Query("SELECT q.id as id, q.version as version,  q.dateCreation as date, q.title as title, q.description as description, q.category as category ,q.status as status from Questionnaire q  WHERE q.id = :id ")
    QuestionnaireProjection findQuestionnaireById(@Param("id") Long questionnaireId);

    Iterable<QuestionnaireProjection> findByTitleContaining(String title);

    @EntityGraph(value = "Questionnaire.questionnaireTags")
    Page<Questionnaire> findAll(Pageable pageable);

    @Override
    @EntityGraph(value = "Questionnaire.questionnaireTags")
    Optional<Questionnaire> findById(Long id);

    @Override
    Page<Questionnaire> findAll(Specification <Questionnaire> specification, Pageable pageable);

}
