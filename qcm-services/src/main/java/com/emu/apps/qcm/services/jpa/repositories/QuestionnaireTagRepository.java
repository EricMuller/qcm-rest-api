package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.qcm.services.jpa.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionnaireTagId;
import com.emu.apps.qcm.services.jpa.entity.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionnaireTagRepository extends JpaRepository<QuestionnaireTag, QuestionnaireTagId> {

    @Query("SELECT qt.tag  from QuestionnaireTag  qt WHERE qt.id.questionnaireId = :id ")
    Iterable<Tag> findByQuestionId(@Param("id") Long questionId);

    @Modifying
    @Query("delete from QuestionnaireTag qt where qt.id.questionnaireId = :id")
    void deleteByQuestionnaireId(@Param("id") Long questionId);

}
