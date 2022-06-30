package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireTagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireTagId;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.TagQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionnaireTagRepository extends JpaRepository<QuestionnaireTagEntity, QuestionnaireTagId>,  JpaSpecificationExecutor <QuestionnaireTagEntity> {

    @Query("SELECT qt.tag  from QuestionnaireTagEntity  qt WHERE qt.id.questionnaireId = :id ")
    Iterable<TagQuestionEntity> findByQuestionId(@Param("id") Long questionId);

    @Modifying
    @Query("delete from QuestionnaireTagEntity qt where qt.id.questionnaireId = :id")
    void deleteByQuestionnaireId(@Param("id") Long questionId);

    Iterable<QuestionnaireTagEntity> findDistinctByDeletedFalseAndQuestionnairePublishedTrue();

    @Query("SELECT distinct qt.tag.libelle  from QuestionnaireTagEntity  qt WHERE qt.questionnaire.published = true ")
    Iterable<String> findDistinctTagLibelleByDeletedFalseAndQuestionnairePublishedTrue();


}
