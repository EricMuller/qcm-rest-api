package com.emu.apps.qcm.services.repositories;

import com.emu.apps.qcm.services.entity.questions.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by eric on 05/06/2017.
 * <p>
 * //   REMEMBER  pb perf
 * //    @Query(value ="SELECT  q.id as id, q.uuid as uuid, q.dateCreation as dateCreation,q.version as version,q.question as question ,q.type as type, q.dateModification as dateModification, qt.id.questionId  as questionId " +
 * //                " from  Question  q left join fetch QuestionTag qt on qt.id.questionId = q.id join fetch qt.tag",
 * //            countQuery = "SELECT COUNT(q) FROM Question q left JOIN  q.questionTags qt join  qt.tag "
 * //    )
 * //    Page<QuestionTagProjection> findAllProjectionQuestionsTags(Pageable pageable); *
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor {

    @Query(value = " SELECT distinct q  from  Question  q left join fetch q.questionTags qt   join fetch qt.tag t",
            countQuery = "SELECT COUNT(distinct q) FROM Question q left JOIN  q.questionTags qt  join  qt.tag t")
    Page<Question> findAllQuestionsTags(Pageable pageable);

    @Query("SELECT q from Question q left join fetch q.questionTags qt join fetch qt.tag WHERE q.id = :id ")
    Question findByIdAndFetchTags(@Param("id") Long id);

    @Query("SELECT q from Question q left join fetch q.questionTags qt " +
            "join fetch qt.tag " +
            "left join fetch q.responses  r " +
            "left join fetch r.choices  " +
            "WHERE q.id = :id ")
    Question findByIdAndFetchTagsAndResponses(@Param("id") Long id);

    @EntityGraph(value = "Question.questionTags")
    Page<Question> findAll(Pageable pageable);

    @Query(value= "SELECT distinct q from Question q  join  q.questionTags qt join  qt.tag WHERE q.id in :ids ",
            countQuery = "SELECT COUNT(distinct q) from Question q  join  q.questionTags qt join  qt.tag WHERE q.id in :ids ")
    @EntityGraph(value = "Question.questionTags")
    Page<Question> findAllByTagIds(@Param("ids") Set<Long> ids, Pageable pageable);
}
