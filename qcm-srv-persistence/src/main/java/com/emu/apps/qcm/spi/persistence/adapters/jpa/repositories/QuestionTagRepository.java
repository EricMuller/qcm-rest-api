package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionTagEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionTagId;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionTagRepository extends JpaRepository<QuestionTagEntity, QuestionTagId> {


    @Query("SELECT qt.tag  from QuestionTagEntity  qt WHERE qt.id.questionId = :id ")
    Iterable<TagEntity> findByQuestionId(@Param("id") Long questionId);


}
