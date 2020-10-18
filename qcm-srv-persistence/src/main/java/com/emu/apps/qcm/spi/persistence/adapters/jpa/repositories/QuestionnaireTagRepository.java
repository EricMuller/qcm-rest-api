package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionnaireTagEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionnaireTagId;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionnaireTagRepository extends JpaRepository<QuestionnaireTagEntity, QuestionnaireTagId> {

    @Query("SELECT qt.tag  from QuestionnaireTagEntity  qt WHERE qt.id.questionnaireId = :id ")
    Iterable<TagEntity> findByQuestionId(@Param("id") Long questionId);

    @Modifying
    @Query("delete from QuestionnaireTagEntity qt where qt.id.questionnaireId = :id")
    void deleteByQuestionnaireId(@Param("id") Long questionId);

    Iterable<QuestionnaireTagEntity> findDistinctByDeletedFalseAndQuestionnairePublishedTrue();

    @Query("SELECT distinct qt.tag.libelle  from QuestionnaireTagEntity  qt WHERE qt.questionnaire.published = true ")
    Iterable<String> findDistinctTagLibelleByDeletedFalseAndQuestionnairePublishedTrue();

}
