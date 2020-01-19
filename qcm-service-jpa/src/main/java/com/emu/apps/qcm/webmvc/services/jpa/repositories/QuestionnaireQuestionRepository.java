package com.emu.apps.qcm.webmvc.services.jpa.repositories;


import com.emu.apps.qcm.webmvc.services.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.webmvc.services.jpa.entity.questionnaires.QuestionnaireQuestionId;
import com.emu.apps.qcm.webmvc.services.jpa.projections.QuestionResponseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionnaireQuestionRepository extends JpaRepository<QuestionnaireQuestion, QuestionnaireQuestionId> {

    @Query("SELECT qq.question.id as id, qq.question.uuid as uuid, qq.question.dateCreation as dateCreation,qq.question.version as version,qq.question.question as question ,qq.question.type as type,qq.position as position " +
            " FROM QuestionnaireQuestion  qq " +
            " WHERE qq.id.questionnaireId = :id  ")
    Iterable<QuestionResponseProjection> findQuestionsByQuestionnaireId(@Param("id") Long questionnaireId);


    @Query("SELECT qq.question.id as id, qq.question.uuid as uuid, qq.question.dateCreation as dateCreation,qq.question.version as version,qq.question.question as question ,qq.question.type as type,qq.position as position " +
            " FROM QuestionnaireQuestion  qq " +
            " WHERE qq.id.questionnaireId = :questionnaireId")
    Page<QuestionResponseProjection> findQuestionsByQuestionnaireId(@Param("questionnaireId") Long questionnaireId, Pageable pageable);

    @Query("SELECT distinct qq.question.id as id, qq.question.uuid as uuid, qq.question.dateCreation as dateCreation,qq.question.version as version,qq.question.question as question ,qq.question.type as type,qq.position as position " +
            " FROM QuestionnaireQuestion qq " +
            " join qq.question q " +
            " left join q.questionTags  qt " +
            " join qt.tag t  " +
            " WHERE qq.id.questionnaireId in :questionnaireIds  and t.id in :tagIds  order by qq.position")
    Page<QuestionResponseProjection> findQuestionsByQuestionnaireIdsAndTagIds(@Param("questionnaireIds") List<Long> questionnaireIds, @Param("tagIds") List<Long> tagIds, Pageable pageable);

    @Modifying
    @Query("delete from QuestionnaireQuestion qq where qq.id.questionnaireId = :id")
    void deleteByQuestionnaireId(@Param("id") Long questionnaireId);


}
