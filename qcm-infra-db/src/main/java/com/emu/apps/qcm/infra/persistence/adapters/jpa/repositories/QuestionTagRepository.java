package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionTagEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.QuestionTagId;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions.TagQuestionEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionTagRepository extends JpaRepository <QuestionTagEntity, QuestionTagId> {


    @Query("SELECT qt.tag  from QuestionTagEntity  qt WHERE qt.id.questionId = :id ")
    Iterable <TagQuestionEntity> findByQuestionId(@Param("id") Long questionId);

    @Query(value = "SELECT distinct qt.tag FROM QuestionTagEntity qt join qt.tag t where t.createdBy = :principal  ",
            countQuery = "SELECT count(distinct qt.tag) FROM QuestionTagEntity qt join qt.tag t where t.createdBy = :principal  ")
    Page <TagQuestionEntity> findAllTagByPrincipal(@Param("principal") String principal, @NotNull Pageable pageable);



}
