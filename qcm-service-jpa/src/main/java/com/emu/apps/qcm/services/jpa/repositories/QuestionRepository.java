package com.emu.apps.qcm.services.jpa.repositories;

import com.emu.apps.qcm.services.entity.questions.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {

    @Query(value = " SELECT distinct q  from  Question  q left join fetch q.questionTags qt   join fetch qt.tag t",
            countQuery = "SELECT COUNT(distinct q) FROM Question q left JOIN  q.questionTags qt  join  qt.tag t")
    Page<Question> findAllQuestionsTags(Pageable pageable);

    @Query("SELECT q from Question q left join fetch q.questionTags qt join fetch qt.tag WHERE q.id = :id ")
    Question findByIdAndFetchTags(@Param("id") Long id);

    @Query("SELECT q from Question q left join fetch q.questionTags qt " +
            "join fetch qt.tag " +
            "left join fetch q.responses  r " +
            "WHERE q.id = :id ")
    Question findByIdAndFetchTagsAndResponses(@Param("id") Long id);

    @EntityGraph(value = "Question.questionTags")
    Page<Question> findAll(Pageable pageable);

    @Override
    Page<Question> findAll(Specification<Question> specification, Pageable pageable);

}
