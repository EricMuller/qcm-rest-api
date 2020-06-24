package com.emu.apps.qcm.infrastructure.adapters.jpa.repositories;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questionnaires.QuestionnaireQuestionId;
import com.emu.apps.qcm.infrastructure.adapters.jpa.projections.QuestionResponseProjection;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionnaireQuestionRepository extends JpaRepository <QuestionnaireQuestion, QuestionnaireQuestionId> {

    @Query("SELECT qq.question.id as id, qq.question.uuid as uuid, qq.question.dateCreation as dateCreation,qq.question.version as version,qq.question.question as question ,qq.question.type as type,qq.position as position " +
            " FROM QuestionnaireQuestion  qq " +
            " WHERE qq.questionnaire.uuid = :questionnaireUuid  ")
    Iterable <QuestionResponseProjection> findQuestionsByQuestionnaireUuiId(@Param("questionnaireUuid") UUID questionnaireUuid);


    @Query("SELECT qq.question.id as id, qq.question.uuid as uuid, qq.question.dateCreation as dateCreation,qq.question.version as version,qq.question.question as question ,qq.question.type as type,qq.position as position " +
            " FROM QuestionnaireQuestion  qq " +
            " WHERE qq.questionnaire.uuid = :questionnaireUuid")
    Page <QuestionResponseProjection> findQuestionsByQuestionnaireUuiId(@Param("questionnaireUuid") UUID questionnaireUuid, Pageable pageable);

    @Query("SELECT distinct qq.question.id as id, qq.question.uuid as uuid, qq.question.dateCreation as dateCreation,qq.question.version as version,qq.question.question as question ,qq.question.type as type,qq.position as position " +
            " FROM QuestionnaireQuestion qq " +
            " join qq.question q " +
            " left join q.questionTags  qt " +
            " join qt.tag t  " +
            " WHERE qq.id.questionnaireId in :questionnaireIds  and t.id in :tagIds  order by qq.position")
    @Deprecated
    Page <QuestionResponseProjection> findQuestionsByQuestionnaireIdsAndTagIds(@Param("questionnaireIds") List <Long> questionnaireIds, @Param("tagIds") List <Long> tagIds, Pageable pageable);

    @Modifying
    @Query("delete from QuestionnaireQuestion qq where qq.id.questionnaireId = :id")
    void deleteByQuestionnaireId(@Param("id") Long questionnaireId);

    @Query("select qq from QuestionnaireQuestion qq where qq.questionnaire.uuid = :uuid and qq.question.uuid = :question_uuid")
    QuestionnaireQuestion findByQuestionUuid(@Param("uuid") UUID uuid, @Param("question_uuid") UUID questionUuid);


    @EntityGraph(value = "QuestionnaireQuestion.question")
    @Query("SELECT qq FROM QuestionnaireQuestion  qq  WHERE qq.questionnaire.uuid = :questionnaireUuid")
    @NotNull
    Iterable <QuestionnaireQuestion> findAllWithTagsAndResponseByQuestionnaireUuid(@Param("questionnaireUuid") UUID uuid);

}
