package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionId;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.projections.QuestionResponseProjection;
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
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionnaireQuestionRepository extends JpaRepository <QuestionnaireQuestionEntity, QuestionnaireQuestionId> {

    @Query("SELECT qq.question.id as id, qq.question.uuid as uuid, qq.question.dateCreation as dateCreation,qq.question.version as version,qq.question.questionText as question ," +
            "qq.question.type as type,qq.position as position " +
            " FROM QuestionnaireQuestionEntity  qq " +
            " WHERE qq.questionnaire.uuid = :questionnaireUuid  ")
    Iterable <QuestionResponseProjection> findQuestionsByQuestionnaireUuiId(@Param("questionnaireUuid") UUID questionnaireUuid);


    @Query("SELECT qq.question.id as id, qq.question.uuid as uuid, qq.question.dateCreation as dateCreation,qq.question.version as version,qq.question.questionText as question ," +
            "qq.question.type as type,qq.position as position" +
            " FROM QuestionnaireQuestionEntity  qq " +
            " WHERE qq.questionnaire.uuid = :questionnaireUuid order by qq.position")
    Page <QuestionResponseProjection> findQuestionsByQuestionnaireUuiId(@Param("questionnaireUuid") UUID questionnaireUuid, Pageable pageable);

//    @Query("SELECT distinct  (q.question.id as id, qq.question.uuid as uuid, qq.question.dateCreation as dateCreation,qq.question.version as version,qq.question.questionText as question ," +
//            " qq.question.type as type,qq.position as position) , q , r as responses" +
//            " FROM QuestionnaireQuestionEntity  qq " +
//            " join qq.question q " +
//            " left join q.responses  r " +
//            " WHERE qq.questionnaire.uuid = :questionnaireUuid order by qq.position")
//    Page <QuestionResponseProjection> findQuestionsAndResponsesByQuestionnaireUuiId(@Param("questionnaireUuid") UUID questionnaireUuid, Pageable pageable);

    /**
     * @param questionnaireIds
     * @param tagIds
     * @param pageable
     * @return
     * @deprecated (" id no longer used ")
     */
    @Query("SELECT distinct qq.question.id as id, qq.question.uuid as uuid, qq.question.dateCreation as dateCreation,qq.question.version as version,qq.question.questionText as question ,qq.question.type as type,qq.position as position " +
            " FROM QuestionnaireQuestionEntity qq " +
            " join qq.question q " +
            " left join q.tags  qt " +
            " join qt.tag t  " +
            " WHERE qq.id.questionnaireId in :questionnaireIds  and t.id in :tagIds  order by qq.position")
    @Deprecated(forRemoval = true)
    Page <QuestionResponseProjection> findQuestionsByQuestionnaireIdsAndTagIds(@Param("questionnaireIds") List <Long> questionnaireIds, @Param("tagIds") List <Long> tagIds, Pageable pageable);

    @Modifying
    @Query("delete from QuestionnaireQuestionEntity qq where qq.id.questionnaireId = :id")
    void deleteByQuestionnaireId(@Param("id") Long questionnaireId);

    @Query("select qq from QuestionnaireQuestionEntity qq where qq.questionnaire.uuid = :uuid and qq.question.uuid = :question_uuid")
    Optional <QuestionnaireQuestionEntity> findByQuestionUuid(@Param("uuid") UUID uuid, @Param("question_uuid") UUID questionUuid);


    @EntityGraph(value = "QuestionnaireQuestion.question")
    @Query("SELECT qq FROM QuestionnaireQuestionEntity  qq  WHERE qq.questionnaire.uuid = :questionnaireUuid")
    @NotNull
    Iterable <QuestionnaireQuestionEntity> findWithTagsAndResponseByQuestionnaireUuid(@Param("questionnaireUuid") UUID uuid);


    @EntityGraph(value = "QuestionnaireQuestion.question")
    @Query("SELECT qq FROM QuestionnaireQuestionEntity  qq   WHERE qq.questionnaire.uuid = :questionnaireUuid")
    @NotNull
    Page <QuestionnaireQuestionEntity> findWithTagsAndResponseByQuestionnaireUuid(@Param("questionnaireUuid") UUID uuid, Pageable pageable);
}
